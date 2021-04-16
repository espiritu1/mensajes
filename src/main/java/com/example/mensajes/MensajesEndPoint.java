package com.example.mensajes;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import me.espiritu.mensajes.BorrarSaludoRequest;
import me.espiritu.mensajes.BorrarSaludoResponse;
import me.espiritu.mensajes.BuscarSaludoNombreRequest;
import me.espiritu.mensajes.BuscarSaludoNombreResponse;
import me.espiritu.mensajes.BuscarSaludosResponse;
import me.espiritu.mensajes.ModificarSaludoRequest;
import me.espiritu.mensajes.ModificarSaludoResponse;
import me.espiritu.mensajes.SaludarRequest;
import me.espiritu.mensajes.SaludarResponse;

@Endpoint
public class MensajesEndPoint {
    @Autowired
    private Isaludadores isaludadores;
/////////////////////                 1                 ////////////////////////////////////////////
    @PayloadRoot(namespace = "http://espiritu.me/mensajes",  localPart ="SaludarRequest")
  
    @ResponsePayload
    public SaludarResponse dameSaludar(@RequestPayload SaludarRequest peticion){
        SaludarResponse respuesta = new SaludarResponse();
        respuesta.setRespuesta("hola "+ peticion.getNombre());

        Saludadores saludadores = new Saludadores();
        saludadores.setNombre(peticion.getNombre());
        isaludadores.save(saludadores);

        return respuesta;
    }
//////////////////////////               2                  ////////////////////////////////////

    @PayloadRoot(namespace = "http://espiritu.me/mensajes",  localPart ="BuscarSaludosRequest")
    
    @ResponsePayload
    public BuscarSaludosResponse dameSaludadores(){
        BuscarSaludosResponse respuesta = new BuscarSaludosResponse();
        Iterable<Saludadores> ListaSaludadores = isaludadores.findAll();
        
        for(Saludadores ls : ListaSaludadores){
            BuscarSaludosResponse.Saludador  saludador = new BuscarSaludosResponse.Saludador();

            saludador.setNombre(ls.getNombre());
            saludador.setId(ls.getId());
            respuesta.getSaludador().add(saludador);
        }

        return respuesta;
    }
//////////////////////////////            3            //////////////////////////////////////////////
     @PayloadRoot(namespace = "http://espiritu.me/mensajes",  localPart ="ModificarSaludoRequest")
     @ResponsePayload

    public ModificarSaludoResponse modificarSaludo(@RequestPayload ModificarSaludoRequest peticion){
        ModificarSaludoResponse respuesta = new ModificarSaludoResponse();

         Optional<Saludadores> s = isaludadores.findById(peticion.getId());

         if(s.isPresent()){
            Saludadores saludadores = new Saludadores();
            saludadores =s.get();
            saludadores.setNombre(peticion.getNombre());
            isaludadores.save(saludadores);
            respuesta.setRespuesta("Se modifico:  " + peticion.getNombre());
         }else{
             respuesta.setRespuesta("Id no existe:  " + peticion.getId());
         }
        return respuesta;
    }
/////////////////////////////                    4            ////////////////////////////////////////

    @PayloadRoot(namespace = "http://espiritu.me/mensajes",  localPart ="BorrarSaludoRequest")
  
    @ResponsePayload
    public BorrarSaludoResponse borraSaludo(@RequestPayload BorrarSaludoRequest peticion){
        BorrarSaludoResponse respuesta = new BorrarSaludoResponse();
        respuesta.setRespuesta(" se borro el id: " + peticion.getId());
        //validar qeu no existe
        isaludadores.deleteById(peticion.getId());

        return respuesta;
    }
//////////////////////////////////           5            /////////////////////////////////////


   /* @PayloadRoot(namespace = "http://espiritu.me/mensajes",  localPart ="BuscarSaludoNombreRequest")
  
    @ResponsePayload
    public BuscarSaludoNombreResponse dameSaludoq(@RequestPayload BuscarSaludoNombreRequest peticion){
        BuscarSaludoNombreResponse respuesta = new BuscarSaludoNombreResponse();
        respuesta.setNombre("hola " + peticion.getNombre());

        Saludadores saludadores = new Saludadores();
        saludadores = isaludadores.finByNombre(peticion.getNombre());

        respuesta.setId(saludadores.getId());
        respuesta.setNombre(saludadores.getNombre());

        return respuesta;
    }*/
    

}