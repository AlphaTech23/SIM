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
import org.example.sim.models.ChoixHoraireDemandeur;
import org.example.sim.models.dto.ChoixHoraireDemandeurSearch;
import org.springframework.web.bind.annotation.*;
import org.example.sim.services.interfaces.ChoixHoraireDemandeurService;
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
@RequestMapping("/choixhorairedemandeurs")
@Tag(name = "Choix horaire demandeur", description = "Choix horaire demandeur Management APIs")
public class ChoixHoraireDemandeurController  {
	private final ChoixHoraireDemandeurService choixhorairedemandeurService;

	public ChoixHoraireDemandeurController(ChoixHoraireDemandeurService choixhorairedemandeurService) {
	   this.choixhorairedemandeurService = choixhorairedemandeurService;
	}

	@Operation(
	    summary = "Retrieve all choix horaire demandeur",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of choix horaire demandeur items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of choix horaire demandeur",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No choix horaire demandeur found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<ChoixHoraireDemandeur>>> getAllChoixHoraireDemandeurs(
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
	    Page<ChoixHoraireDemandeur> choixhorairedemandeurs = choixhorairedemandeurService.getAllChoixHoraireDemandeur(pageable);
	
	    String message = "choix horaire demandeur retrieved successfully";
	
	    if(!choixhorairedemandeurs.hasContent()) {
	            message = "No choix horaire demandeur found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<ChoixHoraireDemandeur>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            choixhorairedemandeurs
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all choix horaire demandeur",
	    security = { @SecurityRequirement(name = "bearerAuth") },
	    description = "Get a paginated and sorted list of choix horaire demandeur items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of choix horaire demandeur",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No choix horaire demandeur found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<ChoixHoraireDemandeur>>> getAllChoixHoraireDemandeurs(
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
	    @RequestBody ChoixHoraireDemandeurSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<ChoixHoraireDemandeur> choixhorairedemandeurs = choixhorairedemandeurService.getAllChoixHoraireDemandeur(pageable, object);
	
	    String message = "choix horaire demandeur retrieved successfully";
	
	    if(!choixhorairedemandeurs.hasContent()) {
	            message = "No choix horaire demandeur found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<ChoixHoraireDemandeur>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            choixhorairedemandeurs
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get choix horaire demandeur by ID",
	    security = { @SecurityRequirement(name = "bearerAuth") },      
	    description = "Retrieve a specific choix horaire demandeur item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the choix horaire demandeur",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Choix horaire demandeur not found with the provided ID"
	    )
	})
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<ChoixHoraireDemandeur>> getChoixHoraireDemandeurById(
	    @Parameter(description = "ID of the choix horaire demandeur to retrieve", required = true)
	    @PathVariable Integer id
	) {
	    ChoixHoraireDemandeur choixhorairedemandeur = choixhorairedemandeurService.getChoixHoraireDemandeurById(id);
	    RestResponse<ChoixHoraireDemandeur> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "choix horaire demandeur retrieved successfully", choixhorairedemandeur);
	    return ResponseEntity.ok(response);
	}
	@Operation(
	     summary = "Export Choix horaire demandeur to CSV",
	     description = "Generate a CSV file from a provided list of Choix horaire demandeur objects."
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
	public ResponseEntity<byte[]> exportChoixHoraireDemandeurToCsv(@RequestBody List<ChoixHoraireDemandeur> choixhorairedemandeur) {
	  
	  String csvContent = choixhorairedemandeurService.exportChoixHoraireDemandeurToCSV(choixhorairedemandeur);
	  
	  return ResponseEntity.ok()
	   .header("Content-Disposition", "attachment; filename=data.csv")
	   .contentType(org.springframework.http.MediaType.TEXT_PLAIN)
	   .body(csvContent.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	}
 

	@Operation(
	    summary = "Create new choix horaire demandeur",
	    security = { @SecurityRequirement(name = "bearerAuth") },
 
	    description = "Create a new choix horaire demandeur item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Choix horaire demandeur created successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid choix horaire demandeur supplied"
	    )
	})
	@PostMapping
	public ResponseEntity<?> createChoixHoraireDemandeur(
	    @Parameter(description = "Choix horaire demandeur object to be created", required = true)
	    @RequestBody @Valid ChoixHoraireDemandeur choixhorairedemandeur
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
	    ChoixHoraireDemandeur newChoixHoraireDemandeur = choixhorairedemandeurService.createChoixHoraireDemandeur(choixhorairedemandeur);
	    RestResponse<ChoixHoraireDemandeur> response = RestResponse.buildSuccessResponse(HttpStatus.CREATED,
	            "choix horaire demandeur created successfully", newChoixHoraireDemandeur);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

 
	@Operation(
	    summary = "Update existing choix horaire demandeur",
	    security = { @SecurityRequirement(name = "bearerAuth") },

	    description = "Update an existing choix horaire demandeur item with the provided information"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Choix horaire demandeur updated successfully",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Choix horaire demandeur not found with the provided ID"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid choix horaire demandeur supplied"
	    )
	})
	@PutMapping("/{id}")
	public ResponseEntity<?> updateChoixHoraireDemandeur(
	    @Parameter(description = "ID of the choix horaire demandeur to update", required = true)
	    @PathVariable Integer id,
	    @Parameter(description = "Updated choix horaire demandeur object", required = true)
	    @RequestBody @Valid ChoixHoraireDemandeur choixhorairedemandeur
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
	    ChoixHoraireDemandeur updateChoixHoraireDemandeur = choixhorairedemandeurService.updateChoixHoraireDemandeur(id, choixhorairedemandeur);
	    RestResponse<ChoixHoraireDemandeur> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "choix horaire demandeur updated successfully", updateChoixHoraireDemandeur);
	    return ResponseEntity.ok(response);
	}


	@Operation(
	    summary = "Delete choix horaire demandeur",
	    security = { @SecurityRequirement(name = "bearerAuth") },    

	    description = "Delete a choix horaire demandeur item by its ID. Returns success even if choix horaire demandeur was already deleted."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Choix horaire demandeur deleted successfully or already deleted"
	    )
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> deleteChoixHoraireDemandeurById(
	    @Parameter(description = "ID of the choix horaire demandeur to delete", required = true)
	    @PathVariable Integer id
	) {
		try {
			choixhorairedemandeurService.getChoixHoraireDemandeurById(id);
			choixhorairedemandeurService.deleteChoixHoraireDemandeur(id);
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"choix horaire demandeur deleted successfully",
					null
				)
			);
		}catch (ResourceNotFoundException ex) {
			return ResponseEntity.ok(
				RestResponse.buildSuccessResponse(
					HttpStatus.OK,
					"choix horaire demandeur already deleted or does not exist",
					null
				)
			);
		}
	}



}
