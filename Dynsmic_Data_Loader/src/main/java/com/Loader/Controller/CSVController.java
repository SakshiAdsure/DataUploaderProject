package com.Loader.Controller;

import com.Loader.Model.User;
import com.Loader.Service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*")  // Allow API to be accessed from anywhere
@RestController
@RequestMapping("/api/users")
public class CSVController {

    @Autowired
    private CSVService csvService;

    // ✅ File Upload API
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a valid CSV file.");
            }
            csvService.saveCSV(file);
            return ResponseEntity.ok("File uploaded successfully and data saved!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing file: " + e.getMessage());
        }
    }

    // ✅ Get All Users API
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = csvService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // ✅ Delete User by ID API
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            csvService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with ID: " + id);
        }
    }
}
