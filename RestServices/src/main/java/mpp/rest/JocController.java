package mpp.rest;

import mpp.model.Joc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/jocuri")
public class JocController {

    private final JocService service;

    public JocController(JocService service) {
        this.service = service;
    }

    @GetMapping("/obtineToate/{id}")
    public ResponseEntity<List<JocDTO>> getJocuri(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getJocuri(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificaJoc(@PathVariable int id, @RequestBody JocDTOModificare dto) {
        try {
            Joc joc = service.update(id, dto);
            return ResponseEntity.status(201).body(joc.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
