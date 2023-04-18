package controller;

import domain.entities.Cat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.implementations.CatServiceImpl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cats")
@Tag(name="Cats")
public class CatController {

    private final CatServiceImpl catService;

    @Autowired
    public CatController(CatServiceImpl catService) {
        this.catService = catService;
    }

    @GetMapping("/findAll")
    @Operation(summary = "Get all cats", description = "Get a list of all cats")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of cats returned successfully"),
            @ApiResponse(responseCode = "404", description = "No cats found")})
    public List<Cat> getAllCats() {
        return catService.getAllCats();
    }

    @GetMapping("/findById/{id}")
    @Operation(summary = "Get cat by id", description = "Get a cat by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cat returned successfully"),
            @ApiResponse(responseCode = "404", description = "No cat found")})
    public ResponseEntity<Cat> getCatById(@PathVariable(value = "id") Long catId) throws SQLException {
        Cat cat = catService.getCatById(catId);
        if (cat.equals(null)) throw new SQLException("Cat not found with id: " + catId);
        return ResponseEntity.ok().body(cat);
    }

    @PostMapping("/add")
    @Operation(summary = "Add cat", description = "add a new cat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cat added successfully"),
            @ApiResponse(responseCode = "404", description = "Can't add a cat")})
    public Cat addCat(@RequestBody Cat cat) {
        return catService.addCat(cat);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update cat by id", description = "Update a cat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cat updated successfully"),
            @ApiResponse(responseCode = "404", description = "Can't update a cat")})
    public ResponseEntity<Cat> updateCat(@PathVariable(value = "id") Long catId,
                                         @RequestBody Cat catDetails) throws SQLException {
        Cat cat = catService.getCatById(catId);
        if (cat.equals(null)) throw new SQLException("Cat not found with id: " + catId);

        cat.setName(catDetails.getName());
        cat.setBreed(catDetails.getBreed());
        cat.setColor(catDetails.getColor());

        final Cat updatedCat = catService.addCat(cat);
        return ResponseEntity.ok(updatedCat);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete cat by id", description = "Delete a cat by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cat deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Can't delete a cat")})
    public Map<String, Boolean> deleteCat(@PathVariable(value = "id") Long catId) throws SQLException {
        Cat cat = catService.getCatById(catId);
        if (cat.equals(null)) throw new SQLException("Cat not found with id: " + catId);

        catService.deleteCat(cat.getId());
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
