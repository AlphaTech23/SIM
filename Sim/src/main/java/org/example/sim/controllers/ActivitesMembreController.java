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
import org.example.sim.models.ActivitesMembre;
import org.example.sim.models.dto.ActivitesMembreSearch;
import org.springframework.web.bind.annotation.*;
import org.example.sim.services.interfaces.ActivitesMembreService;
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
@RequestMapping("/activitesmembres")
@Tag(name = "Activites membre", description = "Activites membre Management APIs")
public class ActivitesMembreController  {
	private final ActivitesMembreService activitesmembreService;

	public ActivitesMembreController(ActivitesMembreService activitesmembreService) {
	   this.activitesmembreService = activitesmembreService;
	}

	@Operation(
	    summary = "Retrieve all activites membre",
	    description = "Get a paginated and sorted list of activites membre items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of activites membre",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No activites membre found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<ActivitesMembre>>> getAllActivitesMembres(
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
	    Page<ActivitesMembre> activitesmembres = activitesmembreService.getAllActivitesMembre(pageable);
	
	    String message = "activites membre retrieved successfully";
	
	    if(!activitesmembres.hasContent()) {
	            message = "No activites membre found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<ActivitesMembre>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            activitesmembres
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all activites membre",
	    description = "Get a paginated and sorted list of activites membre items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of activites membre",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No activites membre found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<ActivitesMembre>>> getAllActivitesMembres(
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
	    @RequestBody ActivitesMembreSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<ActivitesMembre> activitesmembres = activitesmembreService.getAllActivitesMembre(pageable, object);
	
	    String message = "activites membre retrieved successfully";
	
	    if(!activitesmembres.hasContent()) {
	            message = "No activites membre found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<ActivitesMembre>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            activitesmembres
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get activites membre by ID",
	    description = "Retrieve a specific activites membre item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the activites membre",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Activites membre not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<ActivitesMembre>> getActivitesMembreById(
	    @Parameter(description = "ID of the activites membre to retrieve", required = true)
	    @PathVariable Integer id
	) {
	    ActivitesMembre activitesmembre = activitesmembreService.getActivitesMembreById(id);
	    RestResponse<ActivitesMembre> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "activites membre retrieved successfully", activitesmembre);
	    return ResponseEntity.ok(response);
	}
	@Operation(
	     summary = "Export Activites membre to CSV",
	     description = "Generate a CSV file from a provided list of Activites membre objects."
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
	public ResponseEntity<byte[]> exportActivitesMembreToCsv(@RequestBody List<ActivitesMembre> activitesmembre) {
	  
	  String csvContent = activitesmembreService.exportActivitesMembreToCSV(activitesmembre);
	  
	  return ResponseEntity.ok()
	   .header("Content-Disposition", "attachment; filename=data.csv")
	   .contentType(org.springframework.http.MediaType.TEXT_PLAIN)
	   .body(csvContent.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	}
 

	@Operation(
	    summary = "Create new activites membre",
 
	    description = "Create a new activites membre item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Activites membre created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid activites membre supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createActivitesMembre(
	    @Parameter(description = "Activites membre object to be created", required = true)
	    @RequestBody @Valid ActivitesMembre activitesmembre
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
	    ActivitesMembre newActivitesMembre = activitesmembreService.createActivitesMembre(activitesmembre);
	    RestResponse<ActivitesMembre> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "activites membre created successfully", newActivitesMembre);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing activites membre",

	    description = "Update an existing activites membre item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Activites membre updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Activites membre not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid activites membre supplied"
	    )
	})
	@PutMapping("/{id}")
	public ResponseEntity<?> updateActivitesMembre(
	    @Parameter(description = "ID of the activites membre to update", required = true)
	    @PathVariable Integer id,
	    @Parameter(description = "Updated activites membre object", required = true)
	    @RequestBody @Valid ActivitesMembre activitesmembre
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
	    ActivitesMembre updateActivitesMembre = activitesmembreService.updateActivitesMembre(id, activitesmembre);
	    RestResponse<ActivitesMembre> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "activites membre updated successfully", updateActivitesMembre);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete activites membre",

	    description = "Delete a activites membre item by its ID. Returns success even if activites membre was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Activites membre deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deleteActivitesMembreById(
	    @Parameter(description = "ID of the activites membre to delete", required = true)
	    @PathVariable Integer id
	) {
		try {
			activitesmembreService.getActivitesMembreById(id);
			activitesmembreService.deleteActivitesMembre(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"activites membre deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"activites membre already deleted or does not exist",
					null
				)
			);
		}
	}



}
