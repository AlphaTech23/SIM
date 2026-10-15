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
import org.example.sim.models.Sujet;
import org.example.sim.models.dto.SujetSearch;
import org.springframework.web.bind.annotation.*;
import org.example.sim.services.interfaces.SujetService;
import org.example.sim.services.interfaces.SujetsMembreService;
import org.example.sim.models.dto.SujetsMembreSearch;
import org.example.sim.models.SujetsMembre;


import org.example.sim.models.dto.SujetFullDto;

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
@RequestMapping("/sujets")
@Tag(name = "Sujet", description = "Sujet Management APIs")
public class SujetController  {
	private final SujetService sujetService;
private final SujetsMembreService sujetsmembreService;


	public SujetController(SujetService sujetService, SujetsMembreService sujetsmembreService) {
	   this.sujetService = sujetService;
	this.sujetsmembreService = sujetsmembreService;
	
	}

	@Operation(
	    summary = "Retrieve all sujet",
	    description = "Get a paginated and sorted list of sujet items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of sujet",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No sujet found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<Sujet>>> getAllSujets(
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
	    Page<Sujet> sujets = sujetService.getAllSujet(pageable);
	
	    String message = "sujet retrieved successfully";
	
	    if(!sujets.hasContent()) {
	            message = "No sujet found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Sujet>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            sujets
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all sujet",
	    description = "Get a paginated and sorted list of sujet items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of sujet",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No sujet found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<Sujet>>> getAllSujets(
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
	    @RequestBody SujetSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<Sujet> sujets = sujetService.getAllSujet(pageable, object);
	
	    String message = "sujet retrieved successfully";
	
	    if(!sujets.hasContent()) {
	            message = "No sujet found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Sujet>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            sujets
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get sujet by ID",
	    description = "Retrieve a specific sujet item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the sujet",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Sujet not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<Sujet>> getSujetById(
	    @Parameter(description = "ID of the sujet to retrieve", required = true)
	    @PathVariable Integer id
	) {
	    Sujet sujet = sujetService.getSujetById(id);
	    RestResponse<Sujet> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "sujet retrieved successfully", sujet);
	    return ResponseEntity.ok(response);
	}
	@Operation(
	     summary = "Export Sujet to CSV",
	     description = "Generate a CSV file from a provided list of Sujet objects."
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
	public ResponseEntity<byte[]> exportSujetToCsv(@RequestBody List<Sujet> sujet) {
	  
	  String csvContent = sujetService.exportSujetToCSV(sujet);
	  
	  return ResponseEntity.ok()
	   .header("Content-Disposition", "attachment; filename=data.csv")
	   .contentType(org.springframework.http.MediaType.TEXT_PLAIN)
	   .body(csvContent.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	}
 

	@Operation(
	    summary = "Create new sujet",

	    description = "Create a new sujet and its details. The operation is atomic: all or nothing."
	)
	@ApiResponses({
	   @ApiResponse(
	     responseCode = "201",
	     description = "Sujet created successfully",
	     content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class))
	   ),
	   @ApiResponse(
	     responseCode = "400",
	     description = "Validation failed for one or more entities"
	   ),
	   @ApiResponse(
	     responseCode = "500",
	     description = "Internal server error"
	   )
	})
	@PostMapping
	public ResponseEntity<?> createFullSujet(
	    @Parameter(description = "Sujet object to be created with sujetsMembres{{#unless @last}}, {{/unless}}", required = true)
	    @RequestBody @Valid SujetFullDto dto,
	    BindingResult bindingResult
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
	  
	  try {
	    Sujet sujet = dto.getSujet();
	    List<SujetsMembre> sujetsMembres = dto.getSujetsMembres();
	    
	    Sujet createdSujet = sujetService.createFullSujet(sujet, sujetsMembres);
	    RestResponse<Sujet> response = RestResponse.buildSuccessResponse(
	      HttpStatus.CREATED,
	      "Sujet and its details created successfully (Atomic operation)",
	      createdSujet
	    );
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	  } catch (DataIntegrityViolationException ex) {
	    String message = String.format("A database error occurred during the transaction (e.g., constraint violation): %s",
	      	ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage());
	    RestResponse<String> errorResponse = RestResponse.buildErrorResponse(
	      HttpStatus.BAD_REQUEST,
	      message,
	      null
	    );
	    return ResponseEntity.badRequest().body(errorResponse);
	  } catch (Exception ex) {
	    RestResponse<String> errorResponse = RestResponse.buildErrorResponse(
	      HttpStatus.INTERNAL_SERVER_ERROR,
	      String.format("An unexpected error occurred while creating the sujet: %s", ex.getMessage()),
	      null
	    );
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	  }
	}

 
	@Operation(
	    summary = "Update existing sujet",

          description = "Update an existing sujet and its details. The operation is atomic: all or nothing."
      )
      @ApiResponses({
      @ApiResponse(
      responseCode = "200",
      description = "Sujet updated successfully",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class))
      ),
      @ApiResponse(
      responseCode = "404",
      description = "Sujet not found with the provided ID"
      ),
      @ApiResponse(
      responseCode = "400",
      description = "Validation failed or database constraint violation"
      ),
      @ApiResponse(
      responseCode = "500",
      description = "Internal server error"
      )
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFullSujet(
      @Parameter(description = "ID of the sujet to update", required = true)
      @PathVariable Integer id,
      @Parameter(description = "Updated sujet object with sujetsMembres{{#unless @last}}, {{/unless}}", required = true)
      @RequestBody @Valid SujetFullDto dto,
      BindingResult bindingResult
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
      
      try {
        // Check if sujet exists
        Sujet existingSujet = sujetService.getSujetById(id);
        if (existingSujet == null) {
          RestResponse<String> errorResponse = RestResponse.buildErrorResponse(
          HttpStatus.NOT_FOUND,
          String.format("Sujet not found with id: %s", id),
          null
          );
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        
        Sujet sujet = dto.getSujet();
        List<SujetsMembre> sujetsMembres = dto.getSujetsMembres();
        
        
        Sujet updatedSujet = sujetService.updateFullSujet(
            id, sujet, sujetsMembres
        );
        
        RestResponse<Sujet> response = RestResponse.buildSuccessResponse(
            HttpStatus.OK,
            "Sujet and its details updated successfully (Atomic operation)",
            updatedSujet
        );
        return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException ex) {
        RestResponse<String> errorResponse = RestResponse.buildErrorResponse(
            HttpStatus.NOT_FOUND,
            String.format("Sujet not found with id: %s", id),
            null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (DataIntegrityViolationException ex) {
        String message = String.format("A database error occurred during the transaction (e.g., constraint violation): %s", 
            ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage());
        RestResponse<String> errorResponse = RestResponse.buildErrorResponse(
            HttpStatus.BAD_REQUEST,
            message,
            null
        );
        return ResponseEntity.badRequest().body(errorResponse);
      } catch (Exception ex) {
        RestResponse<String> errorResponse = RestResponse.buildErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            String.format("An unexpected error occurred while updating the sujet: %s", ex.getMessage()),
            null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
      }
    }


	@Operation(
	    summary = "Delete sujet",

          description = "Delete an existing sujet and all its associated details. The operation is atomic: all or nothing."
      )
      @ApiResponses({
        @ApiResponse(
          responseCode = "200",
          description = "Sujet deleted successfully"
        ),
        @ApiResponse(
          responseCode = "404",
          description = "Sujet not found with the provided ID"
        ),
        @ApiResponse(
          responseCode = "500",
          description = "Internal server error"
        )
      })
      @DeleteMapping("/{id}")
      public ResponseEntity<RestResponse<Void>> deleteFullSujet(
        @Parameter(description = "ID of the sujet to delete", required = true)
        @PathVariable Integer id
      ) {
        try {
          sujetService.deleteFullSujet(id);
          return ResponseEntity.ok(
            RestResponse.buildSuccessResponse(
              HttpStatus.OK,
              "Sujet deleted successfully",
              null
            )
          );
        } catch (ResourceNotFoundException ex) {
          RestResponse<Void> errorResponse = RestResponse.buildErrorResponse(
            HttpStatus.NOT_FOUND,
            String.format("Sujet not found with id: %s", id),
            null
          );
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception ex) {
          RestResponse<Void> errorResponse = RestResponse.buildErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            String.format("Error while performing atomic deletion of sujet: %s", ex.getMessage()),
            null
          );
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
      } 

@Operation(
  summary = "Get sujetsMembres attached with a sujet ID",
  description = "Retrieve a list of sujetsMembres from a sujet item ID"
)
@ApiResponses({
  @ApiResponse(
    responseCode = "200",
    description = "SujetsMembres retrieved successfully",
    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class))
  ),
  @ApiResponse(
    responseCode = "404",
    description = "Sujet not found with the provided ID"
  ),
  @ApiResponse(
    responseCode = "500",
    description = "Internal server error"
  )
})
@GetMapping("/{id}/sujetsmembres")
public ResponseEntity<?> getSujetsMembresBySujet(
  @Parameter(description = "ID of the sujet to retrieve", required = true)
  @PathVariable Integer id,
  @Parameter(description = "Page number (0-indexed)", required = false)
  @RequestParam(defaultValue = "0") int page,
  @Parameter(description = "Size of the page", required = false)
  @RequestParam(defaultValue = "10") int size,
  @Parameter(description = "Sort order", required = false)
  @RequestParam(defaultValue = "id,asc", required = false) String sortParam
) {
try {
  Sujet sujet = sujetService.getSujetById(id);
  if (sujet == null) {
    RestResponse<String> errorResponse = RestResponse.buildErrorResponse(
      HttpStatus.NOT_FOUND,
      String.format("Sujet not found with id: %s", id),
      null
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }
  Sort sortObj = WebUtils.createSortObject(sortParam);
  Pageable pageable = PageRequest.of(page, size, sortObj);
  SujetsMembreSearch object = new SujetsMembreSearch();
  object.setIdsujetSujets(sujet);
  
  Page<SujetsMembre> sujetsMembresData = sujetsmembreService.getAllSujetsMembre(pageable, object);
  
  RestResponse<Page<SujetsMembre>> response = RestResponse.buildSuccessResponse(
    HttpStatus.OK,
    "SujetsMembres retrieved successfully",
    sujetsMembresData
  );
  return ResponseEntity.ok(response);
} catch (ResourceNotFoundException ex) {
  RestResponse<String> errorResponse = RestResponse.buildErrorResponse(
    HttpStatus.NOT_FOUND,
    ex.getMessage(),
    null
  );
  return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
} catch (Exception ex) {
  RestResponse<String> errorResponse = RestResponse.buildErrorResponse(
    HttpStatus.INTERNAL_SERVER_ERROR,
    String.format("Error while retrieving sujet: %s", ex.getMessage()),
    null
  );
  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
}
}


}
