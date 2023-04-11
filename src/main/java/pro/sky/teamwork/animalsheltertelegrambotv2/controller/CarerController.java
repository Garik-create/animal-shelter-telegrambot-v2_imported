package pro.sky.teamwork.animalsheltertelegrambotv2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.teamwork.animalsheltertelegrambotv2.dto.CarerRecord;
import pro.sky.teamwork.animalsheltertelegrambotv2.model.Carer;
import pro.sky.teamwork.animalsheltertelegrambotv2.service.CarerService;

@RestController
@RequestMapping("/carer")
public class CarerController {
    private final CarerService carerService;

    public CarerController(CarerService carerService) {
        this.carerService = carerService;
    }

    @Operation(
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Добавляемый опекун",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Carer.class),
                            examples = {
                                    @ExampleObject(
                                            value = "{\"id\": 0,"
                                                    + "\"secondName\": \"Иванов\","
                                                    + "\"firstName\": \"Иван\","
                                                    + "\"patronymic\": \"Иванович\","
                                                    + "\"age\": 30,"
                                                    + "\"phoneNumber\": \"+7(999)1234567\""
                                                    + "}"
                                    )
                            }
                    )
            ),
            summary = "Добавление данных опекуна",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Carer added"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }, tags = "Опекун"
    )
    @PostMapping
    public ResponseEntity<CarerRecord> addCarer(@RequestBody CarerRecord carerRecord) {
        return ResponseEntity.ok(this.carerService.addCarer(carerRecord));
    }

    @Operation(
            summary = "Поиск опекуна по ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Carer added",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Carer[].class)))),
                    @ApiResponse(responseCode = "400", description = "Incorrect id",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Carer[].class)))),
                    @ApiResponse(responseCode = "404", description = "Carer with current id not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Carer[].class))))
            },
            tags = "Опекун"
    )
    @GetMapping("/{id}")
    public ResponseEntity<CarerRecord> findCarer(@Parameter(description = "ID Опекуна")
                                           @PathVariable long id) {
        return ResponseEntity.ok(this.carerService.findCarer(id));
    }

    @Operation(
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Редактируемый опекун",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Carer.class),
                            examples = {
                                    @ExampleObject(
                                            value = "{\"id\": 0,"
                                                    + "\"secondName\": \"Иванов\","
                                                    + "\"firstName\": \"Иван\","
                                                    + "\"patronymic\": \"Иванович\","
                                                    + "\"age\": 30,"
                                                    + "\"phoneNumber\": \"+7(999)1234567\""
                                                    + "}"
                                    )
                            }
                    )
            ),
            summary = "Редактирование данных опекуна",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Carer added"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }, tags = "Опекун"
    )
    @PutMapping
    public ResponseEntity<CarerRecord> editCarer(@RequestBody CarerRecord carerRecord) {
        return ResponseEntity.ok(this.carerService.editCarer(carerRecord));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCarer(@Parameter(description = "ID Опекуна")
                                         @PathVariable long id) {
        this.carerService.deleteCarer(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
