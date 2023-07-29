package com.gestion.empleados.controlador;

import com.gestion.empleados.excepciones.ResourceNotFoundException;
import com.gestion.empleados.modelo.Empleado;
import com.gestion.empleados.repositorio.EmpleadoRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class EmpleadoController {

    private EmpleadoRepositorio empleadoRepositorio;

    //Este metodo sirve para listar todos los empleados
    @GetMapping("/empleados")
    public List<Empleado> listarTodosLosEmpleados(){
        return empleadoRepositorio.findAll();
    }

    //Este metodo sirve para guardar el empleado
    @PostMapping(value = "/empleados")
    public Empleado guardarEmpleado(@RequestBody Empleado empleado){
        return empleadoRepositorio.save(empleado);
    }

    //Este metodo sirve para buscar un empleado por Id
    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleado> obtenerEmpleadoPorId(@PathVariable Long id){
        Empleado empleado = empleadoRepositorio.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("No existe el empleado con el ID :" + id)
                );
        return ResponseEntity.ok().body(empleado);
    }

    @PutMapping("/empleados/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado detallesEmpleado){
        Empleado empleado = empleadoRepositorio.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("No existe el empleado con el ID :" + id)
                );
        empleado.setNombre(detallesEmpleado.getNombre());
        empleado.setApellidos(detallesEmpleado.getApellidos());
        empleado.setEmail(detallesEmpleado.getEmail());

        Empleado empleadoActualizado = empleadoRepositorio.save(empleado);
        return ResponseEntity.ok(empleadoActualizado);
    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Map<String,Boolean>> eliminarEmpleadoPorId(@PathVariable Long id){
        Empleado empleado = empleadoRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el empleado con el ID :" + id));

        empleadoRepositorio.delete(empleado);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put(empleado+" eliminado", Boolean.TRUE);

        return ResponseEntity.ok(respuesta);
    }

}
