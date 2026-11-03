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
import org.example.sim.models.TypeOffre;
import org.example.sim.models.dto.TypeOffreSearch;
import org.springframework.web.bind.annotation.*;
import org.example.sim.services.interfaces.TypeOffreService;
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
@RequestMapping("/typeoffres")
@Tag(name = "Type offre", description = "Type offre Management APIs")
public class TypeOffreController  {
	private final TypeOffreService typeoffreService;

	public TypeOffreController(TypeOffreService typeoffreService) {
	   this.typeoffreService = typeoffreService;
	}

	@Operation(
	    summary = "Retrieve all type offre",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of type offre items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of type offre",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No type offre found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<TypeOffre>>> getAllTypeOffres(
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
	    Page<TypeOffre> typeoffres = typeoffreService.getAllTypeOffre(pageable);
	
	    String message = "type offre retrieved successfully";
	
	    if(!typeoffres.hasContent()) {
	            message = "No type offre found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<TypeOffre>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            typeoffres
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all type offre",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of type offre items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of type offre",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No type offre found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<TypeOffre>>> getAllTypeOffres(
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
	    @RequestBody TypeOffreSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<TypeOffre> typeoffres = typeoffreService.getAllTypeOffre(pageable, object);
	
	    String message = "type offre retrieved successfully";
	
	    if(!typeoffres.hasContent()) {
	            message = "No type offre found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<TypeOffre>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            typeoffres
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get type offre by ID",
	    security = { @SecurityRequirement(name = "bearerAuth") },      
	    description = "Retrieve a specific type offre item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the type offre",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Type offre not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<TypeOffre>> getTypeOffreById(
	    @Parameter(description = "ID of the type offre to retrieve", required = true)
	    @PathVariable Integer id
	) {
	    TypeOffre typeoffre = typeoffreService.getTypeOffreById(id);
	    RestResponse<TypeOffre> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "type offre retrieved successfully", typeoffre);
	    return ResponseEntity.ok(response);
	}
	@Operation(
	     summary = "Export Type offre to CSV",
	     description = "Generate a CSV file from a provided list of Type offre objects."
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
	public ResponseEntity<byte[]> exportTypeOffreToCsv(@RequestBody List<TypeOffre> typeoffre) {
	  
	  String csvContent = typeoffreService.exportTypeOffreToCSV(typeoffre);
	  
	  return ResponseEntity.ok()
	   .header("Content-Disposition", "attachment; filename=data.csv")
	   .contentType(org.springframework.http.MediaType.TEXT_PLAIN)
	   .body(csvContent.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	}
 

	@Operation(
	    summary = "Create new type offre",
	    security = { @SecurityRequirement(name = "bearerAuth") },
 
	    description = "Create a new type offre item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Type offre created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid type offre supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createTypeOffre(
	    @Parameter(description = "Type offre object to be created", required = true)
	    @RequestBody @Valid TypeOffre typeoffre
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
	    TypeOffre newTypeOffre = typeoffreService.createTypeOffre(typeoffre);
	    RestResponse<TypeOffre> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "type offre created successfully", newTypeOffre);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing type offre",
	    security = { @SecurityRequirement(name = "bearerAuth") },

	    description = "Update an existing type offre item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Type offre updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Type offre not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid type offre supplied"
	    )
	})
	@PutMapping("/{id}")
	public ResponseEntity<?> updateTypeOffre(
	    @Parameter(description = "ID of the type offre to update", required = true)
	    @PathVariable Integer id,
	    @Parameter(description = "Updated type offre object", required = true)
	    @RequestBody @Valid TypeOffre typeoffre
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
	    TypeOffre updateTypeOffre = typeoffreService.updateTypeOffre(id, typeoffre);
	    RestResponse<TypeOffre> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "type offre updated successfully", updateTypeOffre);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete type offre",
	    security = { @SecurityRequirement(name = "bearerAuth") },    

	    description = "Delete a type offre item by its ID. Returns success even if type offre was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Type offre deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deleteTypeOffreById(
	    @Parameter(description = "ID of the type offre to delete", required = true)
	    @PathVariable Integer id
	) {
		try {
			typeoffreService.getTypeOffreById(id);
			typeoffreService.deleteTypeOffre(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"type offre deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"type offre already deleted or does not exist",
					null
				)
			);
		}
	}



}
