package controller;

import domain.entities.Flea;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.implementations.FleaServiceImpl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/fleas")
@Tag(name="Fleas")
public class FleaController {

    private final FleaServiceImpl fleaService;

    @Autowired
    public FleaController(FleaServiceImpl fleaService) {
        this.fleaService = fleaService;
    }

    @GetMapping("/findAll")
    @Operation(summary = "Get all fleas", description = "Get a list of all fleas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of fleas returned successfully"),
            @ApiResponse(responseCode = "404", description = "No fleas found")})
    public List<Flea> getAllFleas() {
        return fleaService.getAllFleas();
    }

    @GetMapping("/findById/{id}")
    @Operation(summary = "Get flea by id", description = "Get a flea by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "flea returned successfully"),
            @ApiResponse(responseCode = "404", description = "No flea found")})
    public ResponseEntity<Flea> getFleaById(@PathVariable(value = "id") Long fleaId) throws SQLException {
        Flea flea = fleaService.getFleaById(fleaId);
        if (flea.equals(null)) throw new SQLException("flea not found with id: " + fleaId);
        return ResponseEntity.ok().body(flea);
    }

    @PostMapping("/add")
    @Operation(summary = "Add flea", description = "add a new flea")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "flea added successfully"),
            @ApiResponse(responseCode = "404", description = "Can't add a flea")})
    public Flea addFlea(@RequestBody Flea flea) {
        return fleaService.addFlea(flea);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update flea by id", description = "Update a flea")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "flea updated successfully"),
            @ApiResponse(responseCode = "404", description = "Can't update a flea")})
    public ResponseEntity<Flea> updateFlea(@PathVariable(value = "id") Long fleaId,
                                         @RequestBody Flea fleaDetails) throws SQLException {
        Flea flea = fleaService.getFleaById(fleaId);
        if (flea.equals(null)) throw new SQLException("flea not found with id: " + fleaId);

        flea.setName(fleaDetails.getName());

        final Flea updatedflea = fleaService.addFlea(flea);
        return ResponseEntity.ok(updatedflea);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete flea by id", description = "Delete a flea by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "flea deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Can't delete a flea")})
    public Map<String, Boolean> deleteFlea(@PathVariable(value = "id") Long fleaId) throws SQLException {
        Flea flea = fleaService.getFleaById(fleaId);
        if (flea.equals(null)) throw new SQLException("flea not found with id: " + fleaId);

        fleaService.deleteFlea(flea.getId());
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
