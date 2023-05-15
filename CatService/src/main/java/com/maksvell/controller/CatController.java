package com.maksvell.controller;

import com.maksvell.dto.CatDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.maksvell.service.implementations.CatService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cats")
@Tag(name="Cats")
public class CatController {

    private final CatService catService;

    @Autowired
    public CatController(CatService catService) {
        this.catService = catService;
    }

    @GetMapping("/findAll")
    @Operation(summary = "Get all cats", description = "Get a list of all cats")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of cats returned successfully"),
            @ApiResponse(responseCode = "404", description = "No cats found")})
    public List<CatDto> getAllCats() {
        return catService.getAllCats();
    }

    @GetMapping("/findById/{id}")
    @Operation(summary = "Get cat by id", description = "Get a cat by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cat returned successfully"),
            @ApiResponse(responseCode = "404", description = "No cat found")})
    public ResponseEntity<CatDto> getCatById(@PathVariable(value = "id") Long id) {
        CatDto cat = catService.getCatById(id);
        if (cat == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cat not found with id: " + id);
        }
        return ResponseEntity.ok().body(cat);
    }

    @PostMapping("/create")
    @Operation(summary = "Add cat", description = "add a new cat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cat added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")})
    public ResponseEntity<CatDto> addCat(@RequestBody @Valid CatDto catDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request data");
        }
        CatDto savedCat = catService.addCat(catDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCat.getName())
                .toUri();
        return ResponseEntity.created(location).body(savedCat);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update cat by id", description = "Update a cat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cat updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "No cat found")})
    public ResponseEntity<CatDto> updateCat(@PathVariable(value = "id") Long id, @RequestBody @Valid CatDto catDto,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request data");
        }
        CatDto updatedCat = catService.updateCat(id, catDto);
        if (updatedCat == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cat not found with id: " + id);
        }
        return ResponseEntity.ok(updatedCat);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete cat by id", description = "Delete a cat by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cat deleted successfully"),
            @ApiResponse(responseCode = "404", description = "No cat found")})
    public ResponseEntity<Void> deleteCat(@PathVariable(value = "id") Long id) {
        catService.deleteCat(id);
        return ResponseEntity.noContent().build();
    }
}