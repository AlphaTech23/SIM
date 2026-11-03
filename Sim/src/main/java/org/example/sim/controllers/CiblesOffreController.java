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
import org.example.sim.models.CiblesOffre;
import org.example.sim.models.dto.CiblesOffreSearch;
import org.springframework.web.bind.annotation.*;
import org.example.sim.services.interfaces.CiblesOffreService;
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
@RequestMapping("/ciblesoffres")
@Tag(name = "Cibles offre", description = "Cibles offre Management APIs")
public class CiblesOffreController  {
	private final CiblesOffreService ciblesoffreService;

	public CiblesOffreController(CiblesOffreService ciblesoffreService) {
	   this.ciblesoffreService = ciblesoffreService;
	}

	@Operation(
	    summary = "Retrieve all cibles offre",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of cibles offre items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of cibles offre",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No cibles offre found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<CiblesOffre>>> getAllCiblesOffres(
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
	    Page<CiblesOffre> ciblesoffres = ciblesoffreService.getAllCiblesOffre(pageable);
	
	    String message = "cibles offre retrieved successfully";
	
	    if(!ciblesoffres.hasContent()) {
	            message = "No cibles offre found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<CiblesOffre>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            ciblesoffres
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all cibles offre",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of cibles offre items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of cibles offre",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No cibles offre found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<CiblesOffre>>> getAllCiblesOffres(
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
	    @RequestBody CiblesOffreSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<CiblesOffre> ciblesoffres = ciblesoffreService.getAllCiblesOffre(pageable, object);
	
	    String message = "cibles offre retrieved successfully";
	
	    if(!ciblesoffres.hasContent()) {
	            message = "No cibles offre found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<CiblesOffre>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            ciblesoffres
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get cibles offre by ID",
	    security = { @SecurityRequirement(name = "bearerAuth") },      
	    description = "Retrieve a specific cibles offre item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the cibles offre",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Cibles offre not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<CiblesOffre>> getCiblesOffreById(
	    @Parameter(description = "ID of the cibles offre to retrieve", required = true)
	    @PathVariable Integer id
	) {
	    CiblesOffre ciblesoffre = ciblesoffreService.getCiblesOffreById(id);
	    RestResponse<CiblesOffre> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "cibles offre retrieved successfully", ciblesoffre);
	    return ResponseEntity.ok(response);
	}
	@Operation(
	     summary = "Export Cibles offre to CSV",
	     description = "Generate a CSV file from a provided list of Cibles offre objects."
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
	public ResponseEntity<byte[]> exportCiblesOffreToCsv(@RequestBody List<CiblesOffre> ciblesoffre) {
	  
	  String csvContent = ciblesoffreService.exportCiblesOffreToCSV(ciblesoffre);
	  
	  return ResponseEntity.ok()
	   .header("Content-Disposition", "attachment; filename=data.csv")
	   .contentType(org.springframework.http.MediaType.TEXT_PLAIN)
	   .body(csvContent.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	}
 

	@Operation(
	    summary = "Create new cibles offre",
	    security = { @SecurityRequirement(name = "bearerAuth") },
 
	    description = "Create a new cibles offre item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Cibles offre created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid cibles offre supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createCiblesOffre(
	    @Parameter(description = "Cibles offre object to be created", required = true)
	    @RequestBody @Valid CiblesOffre ciblesoffre
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
	    CiblesOffre newCiblesOffre = ciblesoffreService.createCiblesOffre(ciblesoffre);
	    RestResponse<CiblesOffre> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "cibles offre created successfully", newCiblesOffre);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing cibles offre",
	    security = { @SecurityRequirement(name = "bearerAuth") },

	    description = "Update an existing cibles offre item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Cibles offre updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Cibles offre not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid cibles offre supplied"
	    )
	})
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCiblesOffre(
	    @Parameter(description = "ID of the cibles offre to update", required = true)
	    @PathVariable Integer id,
	    @Parameter(description = "Updated cibles offre object", required = true)
	    @RequestBody @Valid CiblesOffre ciblesoffre
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
	    CiblesOffre updateCiblesOffre = ciblesoffreService.updateCiblesOffre(id, ciblesoffre);
	    RestResponse<CiblesOffre> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "cibles offre updated successfully", updateCiblesOffre);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete cibles offre",
	    security = { @SecurityRequirement(name = "bearerAuth") },    

	    description = "Delete a cibles offre item by its ID. Returns success even if cibles offre was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Cibles offre deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deleteCiblesOffreById(
	    @Parameter(description = "ID of the cibles offre to delete", required = true)
	    @PathVariable Integer id
	) {
		try {
			ciblesoffreService.getCiblesOffreById(id);
			ciblesoffreService.deleteCiblesOffre(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"cibles offre deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"cibles offre already deleted or does not exist",
					null
				)
			);
		}
	}



}
