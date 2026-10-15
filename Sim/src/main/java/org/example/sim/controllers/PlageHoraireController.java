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
import org.example.sim.models.PlageHoraire;
import org.example.sim.models.dto.PlageHoraireSearch;
import org.springframework.web.bind.annotation.*;
import org.example.sim.services.interfaces.PlageHoraireService;
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
@RequestMapping("/plagehoraires")
@Tag(name = "Plage horaire", description = "Plage horaire Management APIs")
public class PlageHoraireController  {
	private final PlageHoraireService plagehoraireService;

	public PlageHoraireController(PlageHoraireService plagehoraireService) {
	   this.plagehoraireService = plagehoraireService;
	}

	@Operation(
	    summary = "Retrieve all plage horaire",
	    description = "Get a paginated and sorted list of plage horaire items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of plage horaire",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No plage horaire found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<PlageHoraire>>> getAllPlageHoraires(
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
	    Page<PlageHoraire> plagehoraires = plagehoraireService.getAllPlageHoraire(pageable);
	
	    String message = "plage horaire retrieved successfully";
	
	    if(!plagehoraires.hasContent()) {
	            message = "No plage horaire found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<PlageHoraire>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            plagehoraires
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all plage horaire",
	    description = "Get a paginated and sorted list of plage horaire items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of plage horaire",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No plage horaire found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<PlageHoraire>>> getAllPlageHoraires(
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
	    @RequestBody PlageHoraireSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<PlageHoraire> plagehoraires = plagehoraireService.getAllPlageHoraire(pageable, object);
	
	    String message = "plage horaire retrieved successfully";
	
	    if(!plagehoraires.hasContent()) {
	            message = "No plage horaire found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<PlageHoraire>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            plagehoraires
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get plage horaire by ID",
	    description = "Retrieve a specific plage horaire item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the plage horaire",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Plage horaire not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<PlageHoraire>> getPlageHoraireById(
	    @Parameter(description = "ID of the plage horaire to retrieve", required = true)
	    @PathVariable Integer id
	) {
	    PlageHoraire plagehoraire = plagehoraireService.getPlageHoraireById(id);
	    RestResponse<PlageHoraire> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "plage horaire retrieved successfully", plagehoraire);
	    return ResponseEntity.ok(response);
	}
	@Operation(
	     summary = "Export Plage horaire to CSV",
	     description = "Generate a CSV file from a provided list of Plage horaire objects."
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
	public ResponseEntity<byte[]> exportPlageHoraireToCsv(@RequestBody List<PlageHoraire> plagehoraire) {
	  
	  String csvContent = plagehoraireService.exportPlageHoraireToCSV(plagehoraire);
	  
	  return ResponseEntity.ok()
	   .header("Content-Disposition", "attachment; filename=data.csv")
	   .contentType(org.springframework.http.MediaType.TEXT_PLAIN)
	   .body(csvContent.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	}
 

	@Operation(
	    summary = "Create new plage horaire",
 
	    description = "Create a new plage horaire item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Plage horaire created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid plage horaire supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createPlageHoraire(
	    @Parameter(description = "Plage horaire object to be created", required = true)
	    @RequestBody @Valid PlageHoraire plagehoraire
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
	    PlageHoraire newPlageHoraire = plagehoraireService.createPlageHoraire(plagehoraire);
	    RestResponse<PlageHoraire> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "plage horaire created successfully", newPlageHoraire);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing plage horaire",

	    description = "Update an existing plage horaire item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Plage horaire updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Plage horaire not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid plage horaire supplied"
	    )
	})
	@PutMapping("/{id}")
	public ResponseEntity<?> updatePlageHoraire(
	    @Parameter(description = "ID of the plage horaire to update", required = true)
	    @PathVariable Integer id,
	    @Parameter(description = "Updated plage horaire object", required = true)
	    @RequestBody @Valid PlageHoraire plagehoraire
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
	    PlageHoraire updatePlageHoraire = plagehoraireService.updatePlageHoraire(id, plagehoraire);
	    RestResponse<PlageHoraire> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "plage horaire updated successfully", updatePlageHoraire);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete plage horaire",

	    description = "Delete a plage horaire item by its ID. Returns success even if plage horaire was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Plage horaire deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deletePlageHoraireById(
	    @Parameter(description = "ID of the plage horaire to delete", required = true)
	    @PathVariable Integer id
	) {
		try {
			plagehoraireService.getPlageHoraireById(id);
			plagehoraireService.deletePlageHoraire(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"plage horaire deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"plage horaire already deleted or does not exist",
					null
				)
			);
		}
	}



}
