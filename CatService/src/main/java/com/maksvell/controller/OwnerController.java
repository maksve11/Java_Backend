package com.maksvell.controller;

import com.maksvell.dto.OwnerDto;
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
import com.maksvell.service.implementations.OwnerService;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/owners")
@Tag(name="Owners")
public class OwnerController {

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/findAll")
    @Operation(summary = "Get all owners", description = "Get a list of all owners")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of owners returned successfully"),
            @ApiResponse(responseCode = "404", description = "No owners found")})
    public List<OwnerDto> getAllOwners() {
        return ownerService.getAllOwners();
    }

    @GetMapping("/findById/{id}")
    @Operation(summary = "Get owner by id", description = "Get a owner by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "owner returned successfully"),
            @ApiResponse(responseCode = "404", description = "No owner found")})
    public ResponseEntity<OwnerDto> getOwnerById(@PathVariable(value = "id") Long ownerId) {
        OwnerDto owner = ownerService.getOwnerById(ownerId);
        if (owner == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cat not found with id: " + ownerId);
        }
        return ResponseEntity.ok().body(owner);
    }

    @PostMapping("/create")
    @Operation(summary = "Add Owner", description = "add a new Owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owner added successfully"),
            @ApiResponse(responseCode = "404", description = "Can't add a Owner")})
    public ResponseEntity<OwnerDto> addOwner(@RequestBody @Valid OwnerDto ownerDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request data");
        }
        OwnerDto savedOwner = ownerService.addOwner(ownerDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedOwner.getName())
                .toUri();
        return ResponseEntity.created(location).body(savedOwner);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update Owner by id", description = "Update a Owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owner updated successfully"),
            @ApiResponse(responseCode = "404", description = "Owner not found with the given id")})
    public ResponseEntity<OwnerDto> updateOwner(@PathVariable(value = "id") Long ownerId,
                                              @RequestBody @Valid OwnerDto ownerDto,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request data");
        }
        OwnerDto updatedOwner = ownerService.updateOwner(ownerId, ownerDto);
        if (updatedOwner == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found with id: " + ownerId);
        }
        return ResponseEntity.ok(updatedOwner);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete Owner by id", description = "Delete a Owner by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Owner deleted successfully"),
            @ApiResponse(responseCode = "404", description = "No Owner found")})
    public ResponseEntity<Void> deleteFlea(@PathVariable(value = "id") Long id) {
        ownerService.deleteOwner(id);
        return ResponseEntity.noContent().build();
    }
}
