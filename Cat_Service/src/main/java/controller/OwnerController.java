package controller;

import domain.entities.Owner;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.implementations.OwnerServiceImpl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/owners")
@Tag(name="Owners")
public class OwnerController {

    private final OwnerServiceImpl ownerService;

    @Autowired
    public OwnerController(OwnerServiceImpl ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/findAll")
    @Operation(summary = "Get all owners", description = "Get a list of all owners")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of owners returned successfully"),
            @ApiResponse(responseCode = "404", description = "No owners found")})
    public List<Owner> getAllOwners() {
        return ownerService.getAllOwners();
    }

    @GetMapping("/findById/{id}")
    @Operation(summary = "Get owner by id", description = "Get a owner by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "owner returned successfully"),
            @ApiResponse(responseCode = "404", description = "No owner found")})
    public ResponseEntity<Owner> getOwnerById(@PathVariable(value = "id") Long ownerId) throws SQLException {
        Owner owner = ownerService.getOwnerById(ownerId);
        if (owner.equals(null)) throw new SQLException("Owner not found with id: " + ownerId);
        return ResponseEntity.ok().body(owner);
    }

    @PostMapping("/add")
    @Operation(summary = "Add owner", description = "add a new owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "owner added successfully"),
            @ApiResponse(responseCode = "404", description = "Can't add a owner")})
    public Owner addOwner(@RequestBody Owner owner) {
        return ownerService.addOwner(owner);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update owner by id", description = "Update a owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "owner updated successfully"),
            @ApiResponse(responseCode = "404", description = "Can't update a owner")})
    public ResponseEntity<Owner> updateOwner(@PathVariable(value = "id") Long ownerId,
                                             @RequestBody Owner ownerDetails) throws SQLException {
        Owner owner = ownerService.getOwnerById(ownerId);
        if (owner.equals(null)) throw new SQLException("Owner not found with id: " + ownerId);

        owner.setName(ownerDetails.getName());
        owner.setBirthdate(ownerDetails.getBirthdate());

        final Owner updatedOwner = ownerService.addOwner(owner);
        return ResponseEntity.ok(updatedOwner);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete owner by id", description = "Delete a owner by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "owner deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Can't delete a owner")})
    public Map<String, Boolean> deleteOwner(@PathVariable(value = "id") Long ownerId) throws SQLException {
        Owner owner = ownerService.getOwnerById(ownerId);
        if (owner.equals(null)) throw new SQLException("Owner not found with id: " + ownerId);

        ownerService.deleteOwner(owner.getId());
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
