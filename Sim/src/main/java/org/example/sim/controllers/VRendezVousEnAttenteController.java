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
import org.example.sim.models.VRendezVousEnAttente;
import org.example.sim.models.dto.VRendezVousEnAttenteSearch;
import org.springframework.web.bind.annotation.*;
import org.example.sim.services.interfaces.VRendezVousEnAttenteService;
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
@RequestMapping("/vrendezvousenattentes")
@Tag(name = "V rendez vous en attente", description = "V rendez vous en attente Management APIs")
public class VRendezVousEnAttenteController  {
	private final VRendezVousEnAttenteService vrendezvousenattenteService;

	public VRendezVousEnAttenteController(VRendezVousEnAttenteService vrendezvousenattenteService) {
	   this.vrendezvousenattenteService = vrendezvousenattenteService;
	}

	@Operation(
	    summary = "Retrieve all v rendez vous en attente",
	    description = "Get a paginated and sorted list of v rendez vous en attente items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of v rendez vous en attente",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No v rendez vous en attente found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<VRendezVousEnAttente>>> getAllVRendezVousEnAttentes(
	    @Parameter(description = "Page number (0-indexed)", example = "0")
	    @RequestParam(defaultValue = "0") int page,
	
	    @Parameter(description = "Size of the page", example = "10")
	    @RequestParam(defaultValue = "10") int size,
	
	    @Parameter(
	        description = "Sorting criteria in the format: property,(asc|desc). "
	                + "Multiple sort criteria can be passed using semicolon separator. "
	                + "Example: id,asc;name,desc",
	        example = "idRendezVous,asc"
	    )
	    @RequestParam(defaultValue = "idRendezVous,asc") String sortParam) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<VRendezVousEnAttente> vrendezvousenattentes = vrendezvousenattenteService.getAllVRendezVousEnAttente(pageable);
	
	    String message = "v rendez vous en attente retrieved successfully";
	
	    if(!vrendezvousenattentes.hasContent()) {
	            message = "No v rendez vous en attente found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<VRendezVousEnAttente>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            vrendezvousenattentes
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all v rendez vous en attente",
	    description = "Get a paginated and sorted list of v rendez vous en attente items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of v rendez vous en attente",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No v rendez vous en attente found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<VRendezVousEnAttente>>> getAllVRendezVousEnAttentes(
	    @Parameter(description = "Page number (0-indexed)", example = "0")
	    @RequestParam(defaultValue = "0") int page,
	
	    @Parameter(description = "Size of the page", example = "10")
	    @RequestParam(defaultValue = "10") int size,
	
	    @Parameter(
	        description = "Sorting criteria in the format: property,(asc|desc). "
	                + "Multiple sort criteria can be passed using semicolon separator. "
	                + "Example: idRendezVous,asc;name,desc",
	        example = "idRendezVous,asc"
	    )
	    @RequestParam(defaultValue = "idRendezVous,asc") String sortParam,
	    @RequestBody VRendezVousEnAttenteSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<VRendezVousEnAttente> vrendezvousenattentes = vrendezvousenattenteService.getAllVRendezVousEnAttente(pageable, object);
	
	    String message = "v rendez vous en attente retrieved successfully";
	
	    if(!vrendezvousenattentes.hasContent()) {
	            message = "No v rendez vous en attente found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<VRendezVousEnAttente>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            vrendezvousenattentes
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get v rendez vous en attente by ID",
	    description = "Retrieve a specific v rendez vous en attente item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the v rendez vous en attente",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "V rendez vous en attente not found with the provided ID"
	    )
	})
	@GetMapping("/{idRendezVous}")
	public ResponseEntity<RestResponse<VRendezVousEnAttente>> getVRendezVousEnAttenteById(
	    @Parameter(description = "ID of the v rendez vous en attente to retrieve", required = true)
	    @PathVariable Integer idRendezVous
	) {
	    VRendezVousEnAttente vrendezvousenattente = vrendezvousenattenteService.getVRendezVousEnAttenteById(idRendezVous);
	    RestResponse<VRendezVousEnAttente> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "v rendez vous en attente retrieved successfully", vrendezvousenattente);
	    return ResponseEntity.ok(response);
	}
	@Operation(
	     summary = "Export V rendez vous en attente to CSV",
	     description = "Generate a CSV file from a provided list of V rendez vous en attente objects."
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
	public ResponseEntity<byte[]> exportVRendezVousEnAttenteToCsv(@RequestBody List<VRendezVousEnAttente> vrendezvousenattente) {
	  
	  String csvContent = vrendezvousenattenteService.exportVRendezVousEnAttenteToCSV(vrendezvousenattente);
	  
	  return ResponseEntity.ok()
	   .header("Content-Disposition", "attachment; filename=data.csv")
	   .contentType(org.springframework.http.MediaType.TEXT_PLAIN)
	   .body(csvContent.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	}





}
