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
import org.example.sim.models.Offre;
import org.example.sim.models.dto.OffreSearch;
import org.springframework.web.bind.annotation.*;
import org.example.sim.services.interfaces.OffreService;
import org.example.sim.services.interfaces.CiblesOffreService;
import org.example.sim.models.dto.CiblesOffreSearch;
import org.example.sim.models.CiblesOffre;
import org.example.sim.services.interfaces.HistoOffreService;
import org.example.sim.models.dto.HistoOffreSearch;
import org.example.sim.models.HistoOffre;


import org.example.sim.models.dto.OffreFullDto;

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
@RequestMapping("/offres")
@Tag(name = "Offre", description = "Offre Management APIs")
public class OffreController  {
	private final OffreService offreService;
private final CiblesOffreService ciblesoffreService;
private final HistoOffreService histooffreService;


	public OffreController(OffreService offreService, CiblesOffreService ciblesoffreService, HistoOffreService histooffreService) {
	   this.offreService = offreService;
	this.ciblesoffreService = ciblesoffreService;
	this.histooffreService = histooffreService;
	
	}

	@Operation(
	    summary = "Retrieve all offre",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of offre items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of offre",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No offre found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<Offre>>> getAllOffres(
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
	    Page<Offre> offres = offreService.getAllOffre(pageable);
	
	    String message = "offre retrieved successfully";
	
	    if(!offres.hasContent()) {
	            message = "No offre found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Offre>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            offres
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all offre",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of offre items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of offre",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No offre found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<Offre>>> getAllOffres(
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
	    @RequestBody OffreSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<Offre> offres = offreService.getAllOffre(pageable, object);
	
	    String message = "offre retrieved successfully";
	
	    if(!offres.hasContent()) {
	            message = "No offre found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<Offre>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            offres
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get offre by ID",
	    security = { @SecurityRequirement(name = "bearerAuth") },      
	    description = "Retrieve a specific offre item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the offre",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Offre not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<Offre>> getOffreById(
	    @Parameter(description = "ID of the offre to retrieve", required = true)
	    @PathVariable Integer id
	) {
	    Offre offre = offreService.getOffreById(id);
	    RestResponse<Offre> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "offre retrieved successfully", offre);
	    return ResponseEntity.ok(response);
	}
	@Operation(
	     summary = "Export Offre to CSV",
	     description = "Generate a CSV file from a provided list of Offre objects."
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
	public ResponseEntity<byte[]> exportOffreToCsv(@RequestBody List<Offre> offre) {
	  
	  String csvContent = offreService.exportOffreToCSV(offre);
	  
	  return ResponseEntity.ok()
	   .header("Content-Disposition", "attachment; filename=data.csv")
	   .contentType(org.springframework.http.MediaType.TEXT_PLAIN)
	   .body(csvContent.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	}
 

	@Operation(
	    summary = "Create new offre",
	    security = { @SecurityRequirement(name = "bearerAuth") },

	    description = "Create a new offre and its details. The operation is atomic: all or nothing."
	)
	@ApiResponses({
	   @ApiResponse(
	     responseCode = "201",
	     description = "Offre created successfully",
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
	public ResponseEntity<?> createFullOffre(
	    @Parameter(description = "Offre object to be created with ciblesOffres{{#unless @last}}, {{/unless}}histoOffres{{#unless @last}}, {{/unless}}", required = true)
	    @RequestBody @Valid OffreFullDto dto,
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
	    Offre offre = dto.getOffre();
	    List<CiblesOffre> ciblesOffres = dto.getCiblesOffres();
	    List<HistoOffre> histoOffres = dto.getHistoOffres();
	    
	    Offre createdOffre = offreService.createFullOffre(offre, ciblesOffres, histoOffres);
	    RestResponse<Offre> response = RestResponse.buildSuccessResponse(
	      HttpStatus.CREATED,
	      "Offre and its details created successfully (Atomic operation)",
	      createdOffre
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
	      String.format("An unexpected error occurred while creating the offre: %s", ex.getMessage()),
	      null
	    );
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	  }
	}

 
	@Operation(
	    summary = "Update existing offre",
	    security = { @SecurityRequirement(name = "bearerAuth") },

          description = "Update an existing offre and its details. The operation is atomic: all or nothing."
      )
      @ApiResponses({
      @ApiResponse(
      responseCode = "200",
      description = "Offre updated successfully",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class))
      ),
      @ApiResponse(
      responseCode = "404",
      description = "Offre not found with the provided ID"
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
    public ResponseEntity<?> updateFullOffre(
      @Parameter(description = "ID of the offre to update", required = true)
      @PathVariable Integer id,
      @Parameter(description = "Updated offre object with ciblesOffres{{#unless @last}}, {{/unless}}histoOffres{{#unless @last}}, {{/unless}}", required = true)
      @RequestBody @Valid OffreFullDto dto,
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
        // Check if offre exists
        Offre existingOffre = offreService.getOffreById(id);
        if (existingOffre == null) {
          RestResponse<String> errorResponse = RestResponse.buildErrorResponse(
          HttpStatus.NOT_FOUND,
          String.format("Offre not found with id: %s", id),
          null
          );
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        
        Offre offre = dto.getOffre();
        List<CiblesOffre> ciblesOffres = dto.getCiblesOffres();
        List<HistoOffre> histoOffres = dto.getHistoOffres();
        
        
        Offre updatedOffre = offreService.updateFullOffre(
            id, offre, ciblesOffres, histoOffres
        );
        
        RestResponse<Offre> response = RestResponse.buildSuccessResponse(
            HttpStatus.OK,
            "Offre and its details updated successfully (Atomic operation)",
            updatedOffre
        );
        return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException ex) {
        RestResponse<String> errorResponse = RestResponse.buildErrorResponse(
            HttpStatus.NOT_FOUND,
            String.format("Offre not found with id: %s", id),
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
            String.format("An unexpected error occurred while updating the offre: %s", ex.getMessage()),
            null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
      }
    }


	@Operation(
	    summary = "Delete offre",
	    security = { @SecurityRequirement(name = "bearerAuth") },    

          description = "Delete an existing offre and all its associated details. The operation is atomic: all or nothing."
      )
      @ApiResponses({
        @ApiResponse(
          responseCode = "200",
          description = "Offre deleted successfully"
        ),
        @ApiResponse(
          responseCode = "404",
          description = "Offre not found with the provided ID"
        ),
        @ApiResponse(
          responseCode = "500",
          description = "Internal server error"
        )
      })
      @DeleteMapping("/{id}")
      public ResponseEntity<RestResponse<Void>> deleteFullOffre(
        @Parameter(description = "ID of the offre to delete", required = true)
        @PathVariable Integer id
      ) {
        try {
          offreService.deleteFullOffre(id);
          return ResponseEntity.ok(
            RestResponse.buildSuccessResponse(
              HttpStatus.OK,
              "Offre deleted successfully",
              null
            )
          );
        } catch (ResourceNotFoundException ex) {
          RestResponse<Void> errorResponse = RestResponse.buildErrorResponse(
            HttpStatus.NOT_FOUND,
            String.format("Offre not found with id: %s", id),
            null
          );
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception ex) {
          RestResponse<Void> errorResponse = RestResponse.buildErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            String.format("Error while performing atomic deletion of offre: %s", ex.getMessage()),
            null
          );
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
      } 

@Operation(
  summary = "Get ciblesOffres attached with a offre ID",
  security = { @SecurityRequirement(name = "bearerAuth") },
  description = "Retrieve a list of ciblesOffres from a offre item ID"
)
@ApiResponses({
  @ApiResponse(
    responseCode = "200",
    description = "CiblesOffres retrieved successfully",
    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class))
  ),
  @ApiResponse(
    responseCode = "404",
    description = "Offre not found with the provided ID"
  ),
  @ApiResponse(
    responseCode = "500",
    description = "Internal server error"
  )
})
@GetMapping("/{id}/ciblesoffres")
public ResponseEntity<?> getCiblesOffresByOffre(
  @Parameter(description = "ID of the offre to retrieve", required = true)
  @PathVariable Integer id,
  @Parameter(description = "Page number (0-indexed)", required = false)
  @RequestParam(defaultValue = "0") int page,
  @Parameter(description = "Size of the page", required = false)
  @RequestParam(defaultValue = "10") int size,
  @Parameter(description = "Sort order", required = false)
  @RequestParam(defaultValue = "id,asc", required = false) String sortParam
) {
try {
  Offre offre = offreService.getOffreById(id);
  if (offre == null) {
    RestResponse<String> errorResponse = RestResponse.buildErrorResponse(
      HttpStatus.NOT_FOUND,
      String.format("Offre not found with id: %s", id),
      null
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }
  Sort sortObj = WebUtils.createSortObject(sortParam);
  Pageable pageable = PageRequest.of(page, size, sortObj);
  CiblesOffreSearch object = new CiblesOffreSearch();
  object.setIdoffreOffres(offre);
  
  Page<CiblesOffre> ciblesOffresData = ciblesoffreService.getAllCiblesOffre(pageable, object);
  
  RestResponse<Page<CiblesOffre>> response = RestResponse.buildSuccessResponse(
    HttpStatus.OK,
    "CiblesOffres retrieved successfully",
    ciblesOffresData
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
    String.format("Error while retrieving offre: %s", ex.getMessage()),
    null
  );
  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
}
}
@Operation(
  summary = "Get histoOffres attached with a offre ID",
  security = { @SecurityRequirement(name = "bearerAuth") },
  description = "Retrieve a list of histoOffres from a offre item ID"
)
@ApiResponses({
  @ApiResponse(
    responseCode = "200",
    description = "HistoOffres retrieved successfully",
    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class))
  ),
  @ApiResponse(
    responseCode = "404",
    description = "Offre not found with the provided ID"
  ),
  @ApiResponse(
    responseCode = "500",
    description = "Internal server error"
  )
})
@GetMapping("/{id}/histooffres")
public ResponseEntity<?> getHistoOffresByOffre(
  @Parameter(description = "ID of the offre to retrieve", required = true)
  @PathVariable Integer id,
  @Parameter(description = "Page number (0-indexed)", required = false)
  @RequestParam(defaultValue = "0") int page,
  @Parameter(description = "Size of the page", required = false)
  @RequestParam(defaultValue = "10") int size,
  @Parameter(description = "Sort order", required = false)
  @RequestParam(defaultValue = "id,asc", required = false) String sortParam
) {
try {
  Offre offre = offreService.getOffreById(id);
  if (offre == null) {
    RestResponse<String> errorResponse = RestResponse.buildErrorResponse(
      HttpStatus.NOT_FOUND,
      String.format("Offre not found with id: %s", id),
      null
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }
  Sort sortObj = WebUtils.createSortObject(sortParam);
  Pageable pageable = PageRequest.of(page, size, sortObj);
  HistoOffreSearch object = new HistoOffreSearch();
  object.setIdoffreOffres(offre);
  
  Page<HistoOffre> histoOffresData = histooffreService.getAllHistoOffre(pageable, object);
  
  RestResponse<Page<HistoOffre>> response = RestResponse.buildSuccessResponse(
    HttpStatus.OK,
    "HistoOffres retrieved successfully",
    histoOffresData
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
    String.format("Error while retrieving offre: %s", ex.getMessage()),
    null
  );
  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
}
}


}
