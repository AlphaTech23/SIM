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
import org.example.sim.models.StatutOffre;
import org.example.sim.models.dto.StatutOffreSearch;
import org.springframework.web.bind.annotation.*;
import org.example.sim.services.interfaces.StatutOffreService;
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
@RequestMapping("/statutoffres")
@Tag(name = "Statut offre", description = "Statut offre Management APIs")
public class StatutOffreController  {
	private final StatutOffreService statutoffreService;

	public StatutOffreController(StatutOffreService statutoffreService) {
	   this.statutoffreService = statutoffreService;
	}

	@Operation(
	    summary = "Retrieve all statut offre",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of statut offre items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of statut offre",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No statut offre found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<StatutOffre>>> getAllStatutOffres(
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
	    Page<StatutOffre> statutoffres = statutoffreService.getAllStatutOffre(pageable);
	
	    String message = "statut offre retrieved successfully";
	
	    if(!statutoffres.hasContent()) {
	            message = "No statut offre found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<StatutOffre>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            statutoffres
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all statut offre",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of statut offre items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of statut offre",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No statut offre found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<StatutOffre>>> getAllStatutOffres(
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
	    @RequestBody StatutOffreSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<StatutOffre> statutoffres = statutoffreService.getAllStatutOffre(pageable, object);
	
	    String message = "statut offre retrieved successfully";
	
	    if(!statutoffres.hasContent()) {
	            message = "No statut offre found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<StatutOffre>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            statutoffres
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get statut offre by ID",
	    security = { @SecurityRequirement(name = "bearerAuth") },      
	    description = "Retrieve a specific statut offre item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the statut offre",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Statut offre not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<StatutOffre>> getStatutOffreById(
	    @Parameter(description = "ID of the statut offre to retrieve", required = true)
	    @PathVariable Integer id
	) {
	    StatutOffre statutoffre = statutoffreService.getStatutOffreById(id);
	    RestResponse<StatutOffre> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "statut offre retrieved successfully", statutoffre);
	    return ResponseEntity.ok(response);
	}
	@Operation(
	     summary = "Export Statut offre to CSV",
	     description = "Generate a CSV file from a provided list of Statut offre objects."
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
	public ResponseEntity<byte[]> exportStatutOffreToCsv(@RequestBody List<StatutOffre> statutoffre) {
	  
	  String csvContent = statutoffreService.exportStatutOffreToCSV(statutoffre);
	  
	  return ResponseEntity.ok()
	   .header("Content-Disposition", "attachment; filename=data.csv")
	   .contentType(org.springframework.http.MediaType.TEXT_PLAIN)
	   .body(csvContent.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	}
 

	@Operation(
	    summary = "Create new statut offre",
	    security = { @SecurityRequirement(name = "bearerAuth") },
 
	    description = "Create a new statut offre item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Statut offre created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid statut offre supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createStatutOffre(
	    @Parameter(description = "Statut offre object to be created", required = true)
	    @RequestBody @Valid StatutOffre statutoffre
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
	    StatutOffre newStatutOffre = statutoffreService.createStatutOffre(statutoffre);
	    RestResponse<StatutOffre> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "statut offre created successfully", newStatutOffre);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing statut offre",
	    security = { @SecurityRequirement(name = "bearerAuth") },

	    description = "Update an existing statut offre item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Statut offre updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Statut offre not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid statut offre supplied"
	    )
	})
	@PutMapping("/{id}")
	public ResponseEntity<?> updateStatutOffre(
	    @Parameter(description = "ID of the statut offre to update", required = true)
	    @PathVariable Integer id,
	    @Parameter(description = "Updated statut offre object", required = true)
	    @RequestBody @Valid StatutOffre statutoffre
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
	    StatutOffre updateStatutOffre = statutoffreService.updateStatutOffre(id, statutoffre);
	    RestResponse<StatutOffre> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "statut offre updated successfully", updateStatutOffre);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete statut offre",
	    security = { @SecurityRequirement(name = "bearerAuth") },    

	    description = "Delete a statut offre item by its ID. Returns success even if statut offre was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Statut offre deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deleteStatutOffreById(
	    @Parameter(description = "ID of the statut offre to delete", required = true)
	    @PathVariable Integer id
	) {
		try {
			statutoffreService.getStatutOffreById(id);
			statutoffreService.deleteStatutOffre(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"statut offre deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"statut offre already deleted or does not exist",
					null
				)
			);
		}
	}



}
