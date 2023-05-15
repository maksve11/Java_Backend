package com.maksvell.controller;

import com.maksvell.dto.FleaDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.maksvell.service.implementations.FleaService;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/fleas")
@Tag(name="Fleas")
public class FleaController {

    private final FleaService fleaService;

    @Autowired
    public FleaController(FleaService fleaService) {
        this.fleaService = fleaService;
    }

    @GetMapping("/findAll")
    @Operation(summary = "Get all fleas", description = "Get a list of all fleas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of fleas returned successfully"),
            @ApiResponse(responseCode = "404", description = "No fleas found")})
    public List<FleaDto> getAllFleas() {
        return fleaService.getAllFleas();
    }

    @GetMapping("/findById/{id}")
    @Operation(summary = "Get flea by id", description = "Get a flea by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "flea returned successfully"),
            @ApiResponse(responseCode = "404", description = "No flea found")})
    public ResponseEntity<FleaDto> getFleaById(@PathVariable(value = "id") Long id) {
        FleaDto flea = fleaService.getFleaById(id);
        if (flea == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cat not found with id: " + id);
        }
        return ResponseEntity.ok().body(flea);
    }

    @PostMapping("/create")
    @Operation(summary = "Add flea", description = "add a new flea")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "flea added successfully"),
            @ApiResponse(responseCode = "404", description = "Can't add a flea")})
    public ResponseEntity<FleaDto> addFlea(@RequestBody @Valid FleaDto fleaDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request data");
        }
        FleaDto savedFlea = fleaService.addFlea(fleaDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedFlea.getName())
                .toUri();
        return ResponseEntity.created(location).body(savedFlea);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update flea by id", description = "Update a flea")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "flea updated successfully"),
            @ApiResponse(responseCode = "404", description = "Flea not found with the given id")})
    public ResponseEntity<FleaDto> updateFlea(@PathVariable(value = "id") Long fleaId,
                                              @RequestBody @Valid FleaDto fleaDto,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request data");
        }
        FleaDto updatedFlea = fleaService.updateFlea(fleaId, fleaDto);
        if (updatedFlea == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flea not found with id: " + fleaId);
        }
        return ResponseEntity.ok(updatedFlea);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete flea by id", description = "Delete a flea by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Flea deleted successfully"),
            @ApiResponse(responseCode = "404", description = "No Flea found")})
    public ResponseEntity<Void> deleteFlea(@PathVariable(value = "id") Long id) {
        fleaService.deleteFlea(id);
        return ResponseEntity.noContent().build();
    }
}
