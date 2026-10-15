package org.example.sim.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.example.sim.models.SujetsMembre;
import org.example.sim.models.dto.SujetsMembreSearch;
import org.springframework.web.bind.annotation.*;
import org.example.sim.services.interfaces.SujetsMembreService;
import org.example.sim.utils.WebUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashMap;

import org.example.sim.dto.RestResponse;
import org.example.sim.exception.ResourceNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/sujetsmembres")
@Tag(name = "Sujets membre", description = "Sujets membre Management APIs")
public class SujetsMembreController  {
	private final SujetsMembreService sujetsmembreService;

	public SujetsMembreController(SujetsMembreService sujetsmembreService) {
	   this.sujetsmembreService = sujetsmembreService;
	}

	@Operation(
	    summary = "Retrieve all sujets membre",
	    description = "Get a paginated and sorted list of sujets membre items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of sujets membre",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No sujets membre found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<SujetsMembre>>> getAllSujetsMembres(
	    @Parameter(description = "Page number (0-indexed)", example = "0")
	    @RequestParam(defaultValue = "0") int page,
	
	    @Parameter(description = "Size of the page", example = "10")
	    @RequestParam(defaultValue = "10") int size,
	
	    @Parameter(
	        description = "Sorting criteria in the format: property,(asc|desc). "
	                + "Multiple sort criteria can be passed using semicolon separator. "
	                + "Example: id,asc;name,desc",
	        example = "id,asc"
	    )
	    @RequestParam(defaultValue = "id,asc") String sortParam) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<SujetsMembre> sujetsmembres = sujetsmembreService.getAllSujetsMembre(pageable);
	
	    String message = "sujets membre retrieved successfully";
	
	    if(!sujetsmembres.hasContent()) {
	            message = "No sujets membre found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<SujetsMembre>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            sujetsmembres
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all sujets membre",
	    description = "Get a paginated and sorted list of sujets membre items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of sujets membre",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No sujets membre found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<SujetsMembre>>> getAllSujetsMembres(
	    @Parameter(description = "Page number (0-indexed)", example = "0")
	    @RequestParam(defaultValue = "0") int page,
	
	    @Parameter(description = "Size of the page", example = "10")
	    @RequestParam(defaultValue = "10") int size,
	
	    @Parameter(
	        description = "Sorting criteria in the format: property,(asc|desc). "
	                + "Multiple sort criteria can be passed using semicolon separator. "
	                + "Example: id,asc;name,desc",
	        example = "id,asc"
	    )
	    @RequestParam(defaultValue = "id,asc") String sortParam,
	    @RequestBody SujetsMembreSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<SujetsMembre> sujetsmembres = sujetsmembreService.getAllSujetsMembre(pageable, object);
	
	    String message = "sujets membre retrieved successfully";
	
	    if(!sujetsmembres.hasContent()) {
	            message = "No sujets membre found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<SujetsMembre>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            sujetsmembres
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get sujets membre by ID",
	    description = "Retrieve a specific sujets membre item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the sujets membre",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Sujets membre not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<SujetsMembre>> getSujetsMembreById(
	    @Parameter(description = "ID of the sujets membre to retrieve", required = true)
	    @PathVariable Integer id
	) {
	    SujetsMembre sujetsmembre = sujetsmembreService.getSujetsMembreById(id);
	    RestResponse<SujetsMembre> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "sujets membre retrieved successfully", sujetsmembre);
	    return ResponseEntity.ok(response);
	}
	@Operation(
	     summary = "Export Sujets membre to CSV",
	     description = "Generate a CSV file from a provided list of Sujets membre objects."
	)
	@ApiResponses({
	   @ApiResponse(
	   responseCode = "200",
	   description = "CSV file successfully generated",
	   content = @Content(mediaType = "text/csv")
	   ),
	   @ApiResponse(
	   responseCode = "400",
	   description = "Invalid or empty list provided"
	   )
	})
	@PostMapping(value = "/export/csv", consumes = "application/json", produces = "text/csv")
	public ResponseEntity<byte[]> exportSujetsMembreToCsv(@RequestBody List<SujetsMembre> sujetsmembre) {
	  
	  String csvContent = sujetsmembreService.exportSujetsMembreToCSV(sujetsmembre);
	  
	  return ResponseEntity.ok()
	   .header("Content-Disposition", "attachment; filename=data.csv")
	   .contentType(org.springframework.http.MediaType.TEXT_PLAIN)
	   .body(csvContent.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	}
 

	@Operation(
	    summary = "Create new sujets membre",
 
	    description = "Create a new sujets membre item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Sujets membre created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid sujets membre supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createSujetsMembre(
	    @Parameter(description = "Sujets membre object to be created", required = true)
	    @RequestBody @Valid SujetsMembre sujetsmembre
	    ,BindingResult bindingResult
	) {
	    if (bindingResult.hasErrors()) {
	       HashMap<String, String> errors = new HashMap<>();
	       bindingResult.getFieldErrors().forEach(error -> {
	           errors.put(error.getField(), error.getDefaultMessage());
	       });
	       RestResponse<HashMap<String, String>> errorResponse =
	       RestResponse.buildErrorResponse(HttpStatus.BAD_REQUEST,
	       "Validation failed", errors);
	
	       return ResponseEntity.badRequest().body(errorResponse);
	    }
	    SujetsMembre newSujetsMembre = sujetsmembreService.createSujetsMembre(sujetsmembre);
	    RestResponse<SujetsMembre> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "sujets membre created successfully", newSujetsMembre);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing sujets membre",

	    description = "Update an existing sujets membre item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Sujets membre updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Sujets membre not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid sujets membre supplied"
	    )
	})
	@PutMapping("/{id}")
	public ResponseEntity<?> updateSujetsMembre(
	    @Parameter(description = "ID of the sujets membre to update", required = true)
	    @PathVariable Integer id,
	    @Parameter(description = "Updated sujets membre object", required = true)
	    @RequestBody @Valid SujetsMembre sujetsmembre
	    ,BindingResult bindingResult
	) {
	    if (bindingResult.hasErrors()) {
	       HashMap<String, String> errors = new HashMap<>();
	       bindingResult.getFieldErrors().forEach(error -> {
	           errors.put(error.getField(), error.getDefaultMessage());
	       });
	       RestResponse<HashMap<String, String>> errorResponse =
	       RestResponse.buildErrorResponse(HttpStatus.BAD_REQUEST,
	       "Validation failed", errors);
	
	       return ResponseEntity.badRequest().body(errorResponse);
	    }
	    SujetsMembre updateSujetsMembre = sujetsmembreService.updateSujetsMembre(id, sujetsmembre);
	    RestResponse<SujetsMembre> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "sujets membre updated successfully", updateSujetsMembre);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete sujets membre",

	    description = "Delete a sujets membre item by its ID. Returns success even if sujets membre was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Sujets membre deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deleteSujetsMembreById(
	    @Parameter(description = "ID of the sujets membre to delete", required = true)
	    @PathVariable Integer id
	) {
		try {
			sujetsmembreService.getSujetsMembreById(id);
			sujetsmembreService.deleteSujetsMembre(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"sujets membre deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"sujets membre already deleted or does not exist",
					null
				)
			);
		}
	}



}
