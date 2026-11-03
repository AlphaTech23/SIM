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
import org.example.sim.models.HistoOffre;
import org.example.sim.models.dto.HistoOffreSearch;
import org.springframework.web.bind.annotation.*;
import org.example.sim.services.interfaces.HistoOffreService;
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
@RequestMapping("/histooffres")
@Tag(name = "Histo offre", description = "Histo offre Management APIs")
public class HistoOffreController  {
	private final HistoOffreService histooffreService;

	public HistoOffreController(HistoOffreService histooffreService) {
	   this.histooffreService = histooffreService;
	}

	@Operation(
	    summary = "Retrieve all histo offre",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of histo offre items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of histo offre",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No histo offre found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<HistoOffre>>> getAllHistoOffres(
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
	    Page<HistoOffre> histooffres = histooffreService.getAllHistoOffre(pageable);
	
	    String message = "histo offre retrieved successfully";
	
	    if(!histooffres.hasContent()) {
	            message = "No histo offre found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<HistoOffre>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            histooffres
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all histo offre",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of histo offre items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of histo offre",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No histo offre found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<HistoOffre>>> getAllHistoOffres(
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
	    @RequestBody HistoOffreSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<HistoOffre> histooffres = histooffreService.getAllHistoOffre(pageable, object);
	
	    String message = "histo offre retrieved successfully";
	
	    if(!histooffres.hasContent()) {
	            message = "No histo offre found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<HistoOffre>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            histooffres
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get histo offre by ID",
	    security = { @SecurityRequirement(name = "bearerAuth") },      
	    description = "Retrieve a specific histo offre item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the histo offre",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Histo offre not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<HistoOffre>> getHistoOffreById(
	    @Parameter(description = "ID of the histo offre to retrieve", required = true)
	    @PathVariable Integer id
	) {
	    HistoOffre histooffre = histooffreService.getHistoOffreById(id);
	    RestResponse<HistoOffre> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "histo offre retrieved successfully", histooffre);
	    return ResponseEntity.ok(response);
	}
	@Operation(
	     summary = "Export Histo offre to CSV",
	     description = "Generate a CSV file from a provided list of Histo offre objects."
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
	public ResponseEntity<byte[]> exportHistoOffreToCsv(@RequestBody List<HistoOffre> histooffre) {
	  
	  String csvContent = histooffreService.exportHistoOffreToCSV(histooffre);
	  
	  return ResponseEntity.ok()
	   .header("Content-Disposition", "attachment; filename=data.csv")
	   .contentType(org.springframework.http.MediaType.TEXT_PLAIN)
	   .body(csvContent.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	}
 

	@Operation(
	    summary = "Create new histo offre",
	    security = { @SecurityRequirement(name = "bearerAuth") },
 
	    description = "Create a new histo offre item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Histo offre created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid histo offre supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createHistoOffre(
	    @Parameter(description = "Histo offre object to be created", required = true)
	    @RequestBody @Valid HistoOffre histooffre
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
	    HistoOffre newHistoOffre = histooffreService.createHistoOffre(histooffre);
	    RestResponse<HistoOffre> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "histo offre created successfully", newHistoOffre);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing histo offre",
	    security = { @SecurityRequirement(name = "bearerAuth") },

	    description = "Update an existing histo offre item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Histo offre updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Histo offre not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid histo offre supplied"
	    )
	})
	@PutMapping("/{id}")
	public ResponseEntity<?> updateHistoOffre(
	    @Parameter(description = "ID of the histo offre to update", required = true)
	    @PathVariable Integer id,
	    @Parameter(description = "Updated histo offre object", required = true)
	    @RequestBody @Valid HistoOffre histooffre
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
	    HistoOffre updateHistoOffre = histooffreService.updateHistoOffre(id, histooffre);
	    RestResponse<HistoOffre> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "histo offre updated successfully", updateHistoOffre);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete histo offre",
	    security = { @SecurityRequirement(name = "bearerAuth") },    

	    description = "Delete a histo offre item by its ID. Returns success even if histo offre was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Histo offre deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deleteHistoOffreById(
	    @Parameter(description = "ID of the histo offre to delete", required = true)
	    @PathVariable Integer id
	) {
		try {
			histooffreService.getHistoOffreById(id);
			histooffreService.deleteHistoOffre(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"histo offre deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"histo offre already deleted or does not exist",
					null
				)
			);
		}
	}



}
