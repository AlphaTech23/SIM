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
import org.example.sim.models.RendezVou;
import org.example.sim.models.dto.RendezVouSearch;
import org.springframework.web.bind.annotation.*;
import org.example.sim.services.interfaces.RendezVouService;
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
@RequestMapping("/rendezvous")
@Tag(name = "Rendez vou", description = "Rendez vou Management APIs")
public class RendezVouController  {
	private final RendezVouService rendezvouService;

	public RendezVouController(RendezVouService rendezvouService) {
	   this.rendezvouService = rendezvouService;
	}

	@Operation(
	    summary = "Retrieve all rendez vou",
	    description = "Get a paginated and sorted list of rendez vou items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of rendez vou",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No rendez vou found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<RendezVou>>> getAllRendezVous(
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
	    Page<RendezVou> rendezvous = rendezvouService.getAllRendezVou(pageable);
	
	    String message = "rendez vou retrieved successfully";
	
	    if(!rendezvous.hasContent()) {
	            message = "No rendez vou found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<RendezVou>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            rendezvous
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all rendez vou",
	    description = "Get a paginated and sorted list of rendez vou items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of rendez vou",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No rendez vou found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<RendezVou>>> getAllRendezVous(
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
	    @RequestBody RendezVouSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<RendezVou> rendezvous = rendezvouService.getAllRendezVou(pageable, object);
	
	    String message = "rendez vou retrieved successfully";
	
	    if(!rendezvous.hasContent()) {
	            message = "No rendez vou found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<RendezVou>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            rendezvous
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get rendez vou by ID",
	    description = "Retrieve a specific rendez vou item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the rendez vou",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Rendez vou not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<RendezVou>> getRendezVouById(
	    @Parameter(description = "ID of the rendez vou to retrieve", required = true)
	    @PathVariable Integer id
	) {
	    RendezVou rendezvou = rendezvouService.getRendezVouById(id);
	    RestResponse<RendezVou> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "rendez vou retrieved successfully", rendezvou);
	    return ResponseEntity.ok(response);
	}
	@Operation(
	     summary = "Export Rendez vou to CSV",
	     description = "Generate a CSV file from a provided list of Rendez vou objects."
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
	public ResponseEntity<byte[]> exportRendezVouToCsv(@RequestBody List<RendezVou> rendezvou) {
	  
	  String csvContent = rendezvouService.exportRendezVouToCSV(rendezvou);
	  
	  return ResponseEntity.ok()
	   .header("Content-Disposition", "attachment; filename=data.csv")
	   .contentType(org.springframework.http.MediaType.TEXT_PLAIN)
	   .body(csvContent.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	}
 

	@Operation(
	    summary = "Create new rendez vou",
 
	    description = "Create a new rendez vou item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Rendez vou created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid rendez vou supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createRendezVou(
	    @Parameter(description = "Rendez vou object to be created", required = true)
	    @RequestBody @Valid RendezVou rendezvou
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
	    RendezVou newRendezVou = rendezvouService.createRendezVou(rendezvou);
	    RestResponse<RendezVou> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "rendez vou created successfully", newRendezVou);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing rendez vou",

	    description = "Update an existing rendez vou item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Rendez vou updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Rendez vou not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid rendez vou supplied"
	    )
	})
	@PutMapping("/{id}")
	public ResponseEntity<?> updateRendezVou(
	    @Parameter(description = "ID of the rendez vou to update", required = true)
	    @PathVariable Integer id,
	    @Parameter(description = "Updated rendez vou object", required = true)
	    @RequestBody @Valid RendezVou rendezvou
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
	    RendezVou updateRendezVou = rendezvouService.updateRendezVou(id, rendezvou);
	    RestResponse<RendezVou> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "rendez vou updated successfully", updateRendezVou);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete rendez vou",

	    description = "Delete a rendez vou item by its ID. Returns success even if rendez vou was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Rendez vou deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deleteRendezVouById(
	    @Parameter(description = "ID of the rendez vou to delete", required = true)
	    @PathVariable Integer id
	) {
		try {
			rendezvouService.getRendezVouById(id);
			rendezvouService.deleteRendezVou(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"rendez vou deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"rendez vou already deleted or does not exist",
					null
				)
			);
		}
	}



}
