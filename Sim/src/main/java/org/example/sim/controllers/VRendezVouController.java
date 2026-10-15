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
import org.example.sim.models.VRendezVou;
import org.example.sim.models.dto.VRendezVouSearch;
import org.springframework.web.bind.annotation.*;
import org.example.sim.services.interfaces.VRendezVouService;
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
@RequestMapping("/vrendezvous")
@Tag(name = "V rendez vou", description = "V rendez vou Management APIs")
public class VRendezVouController  {
	private final VRendezVouService vrendezvouService;

	public VRendezVouController(VRendezVouService vrendezvouService) {
	   this.vrendezvouService = vrendezvouService;
	}

	@Operation(
	    summary = "Retrieve all v rendez vou",
	    description = "Get a paginated and sorted list of v rendez vou items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of v rendez vou",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No v rendez vou found"
	    )
	})
	@GetMapping
	public ResponseEntity<RestResponse<Page<VRendezVou>>> getAllVRendezVous(
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
	    Page<VRendezVou> vrendezvous = vrendezvouService.getAllVRendezVou(pageable);
	
	    String message = "v rendez vou retrieved successfully";
	
	    if(!vrendezvous.hasContent()) {
	            message = "No v rendez vou found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<VRendezVou>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            vrendezvous
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Retrieve all v rendez vou",
	    description = "Get a paginated and sorted list of v rendez vou items. Returns an empty list if no data is found."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the paginated list of v rendez vou",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "204",
	        description = "No v rendez vou found"
	    )
	})
	@PostMapping("/search")
	public ResponseEntity<RestResponse<Page<VRendezVou>>> getAllVRendezVous(
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
	    @RequestBody VRendezVouSearch object) {
	
	    Sort sortObj = WebUtils.createSortObject(sortParam);
	    Pageable pageable = PageRequest.of(page, size, sortObj);
	    Page<VRendezVou> vrendezvous = vrendezvouService.getAllVRendezVou(pageable, object);
	
	    String message = "v rendez vou retrieved successfully";
	
	    if(!vrendezvous.hasContent()) {
	            message = "No v rendez vou found";
	            throw new ResourceNotFoundException(message);
	    }
	
	    RestResponse<Page<VRendezVou>> response = RestResponse.buildSuccessResponse(
	            HttpStatus.OK,
	            message,
	            vrendezvous
	    );
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@Operation(
	    summary = "Get v rendez vou by ID",
	    description = "Retrieve a specific v rendez vou item by its ID"
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Successfully retrieved the v rendez vou",
	        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "V rendez vou not found with the provided ID"
	    )
	})
	@GetMapping("/{idRendezVous}")
	public ResponseEntity<RestResponse<VRendezVou>> getVRendezVouById(
	    @Parameter(description = "ID of the v rendez vou to retrieve", required = true)
	    @PathVariable Integer idRendezVous
	) {
	    VRendezVou vrendezvou = vrendezvouService.getVRendezVouById(idRendezVous);
	    RestResponse<VRendezVou> response = RestResponse.buildSuccessResponse(HttpStatus.OK,
	            "v rendez vou retrieved successfully", vrendezvou);
	    return ResponseEntity.ok(response);
	}
	@Operation(
	     summary = "Export V rendez vou to CSV",
	     description = "Generate a CSV file from a provided list of V rendez vou objects."
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
	public ResponseEntity<byte[]> exportVRendezVouToCsv(@RequestBody List<VRendezVou> vrendezvou) {
	  
	  String csvContent = vrendezvouService.exportVRendezVouToCSV(vrendezvou);
	  
	  return ResponseEntity.ok()
	   .header("Content-Disposition", "attachment; filename=data.csv")
	   .contentType(org.springframework.http.MediaType.TEXT_PLAIN)
	   .body(csvContent.getBytes(java.nio.charset.StandardCharsets.UTF_8));
	}





}
