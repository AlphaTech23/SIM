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
import org.example.sim.models.StatutRendezVou;
import org.example.sim.models.dto.StatutRendezVouSearch;
import org.springframework.web.bind.annotation.*;
import org.example.sim.services.interfaces.StatutRendezVouService;
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
@RequestMapping("/statutrendezvous")
@Tag(name = "Statut rendez vou", description = "Statut rendez vou Management APIs")
public class StatutRendezVouController  {
	private final StatutRendezVouService statutrendezvouService;

	public StatutRendezVouController(StatutRendezVouService statutrendezvouService) {
	   this.statutrendezvouService = statutrendezvouService;
	}

	@Operation(
	    summary = "Retrieve all statut rendez vou",
	    description = "Get a paginated and sorted list of statut rendez vou items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of statut rendez vou",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No statut rendez vou found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<StatutRendezVou>>> getAllStatutRendezVous(
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
	    Page<StatutRendezVou> statutrendezvous = statutrendezvouService.getAllStatutRendezVou(pageable);
	
	    String message = "statut rendez vou retrieved successfully";
	
	    if(!statutrendezvous.hasContent()) {
	            message = "No statut rendez vou found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<StatutRendezVou>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            statutrendezvous
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all statut rendez vou",
	    description = "Get a paginated and sorted list of statut rendez vou items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of statut rendez vou",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No statut rendez vou found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<StatutRendezVou>>> getAllStatutRendezVous(
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
	    @RequestBody StatutRendezVouSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<StatutRendezVou> statutrendezvous = statutrendezvouService.getAllStatutRendezVou(pageable, object);
	
	    String message = "statut rendez vou retrieved successfully";
	
	    if(!statutrendezvous.hasContent()) {
	            message = "No statut rendez vou found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<StatutRendezVou>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            statutrendezvous
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get statut rendez vou by ID",
	    description = "Retrieve a specific statut rendez vou item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the statut rendez vou",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Statut rendez vou not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<StatutRendezVou>> getStatutRendezVouById(
	    @Parameter(description = "ID of the statut rendez vou to retrieve", required = true)
	    @PathVariable Integer id
	) {
	    StatutRendezVou statutrendezvou = statutrendezvouService.getStatutRendezVouById(id);
	    RestResponse<StatutRendezVou> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "statut rendez vou retrieved successfully", statutrendezvou);
	    return ResponseEntity.ok(response);
	}
	@Operation(
	     summary = "Export Statut rendez vou to CSV",
	     description = "Generate a CSV file from a provided list of Statut rendez vou objects."
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
	public ResponseEntity<byte[]> exportStatutRendezVouToCsv(@RequestBody List<StatutRendezVou> statutrendezvou) {
	  
	  String csvContent = statutrendezvouService.exportStatutRendezVouToCSV(statutrendezvou);
	  
	  return ResponseEntity.ok()
	   .header("Content-Disposition", "attachment; filename=data.csv")
	   .contentType(org.springframework.http.MediaType.TEXT_PLAIN)
	   .body(csvContent.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	}
 

	@Operation(
	    summary = "Create new statut rendez vou",
 
	    description = "Create a new statut rendez vou item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Statut rendez vou created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid statut rendez vou supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createStatutRendezVou(
	    @Parameter(description = "Statut rendez vou object to be created", required = true)
	    @RequestBody @Valid StatutRendezVou statutrendezvou
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
	    StatutRendezVou newStatutRendezVou = statutrendezvouService.createStatutRendezVou(statutrendezvou);
	    RestResponse<StatutRendezVou> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "statut rendez vou created successfully", newStatutRendezVou);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing statut rendez vou",

	    description = "Update an existing statut rendez vou item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Statut rendez vou updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Statut rendez vou not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid statut rendez vou supplied"
	    )
	})
	@PutMapping("/{id}")
	public ResponseEntity<?> updateStatutRendezVou(
	    @Parameter(description = "ID of the statut rendez vou to update", required = true)
	    @PathVariable Integer id,
	    @Parameter(description = "Updated statut rendez vou object", required = true)
	    @RequestBody @Valid StatutRendezVou statutrendezvou
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
	    StatutRendezVou updateStatutRendezVou = statutrendezvouService.updateStatutRendezVou(id, statutrendezvou);
	    RestResponse<StatutRendezVou> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "statut rendez vou updated successfully", updateStatutRendezVou);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete statut rendez vou",

	    description = "Delete a statut rendez vou item by its ID. Returns success even if statut rendez vou was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Statut rendez vou deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deleteStatutRendezVouById(
	    @Parameter(description = "ID of the statut rendez vou to delete", required = true)
	    @PathVariable Integer id
	) {
		try {
			statutrendezvouService.getStatutRendezVouById(id);
			statutrendezvouService.deleteStatutRendezVou(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"statut rendez vou deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"statut rendez vou already deleted or does not exist",
					null
				)
			);
		}
	}



}
