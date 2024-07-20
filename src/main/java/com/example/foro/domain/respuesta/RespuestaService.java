package com.example.foro.domain.respuesta;

import com.example.foro.domain.topico.Topico;
import com.example.foro.domain.topico.TopicoRepository;
import com.example.foro.domain.usuario.Usuario;
import com.example.foro.domain.usuario.UsuarioRepository;
import com.example.foro.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RespuestaService {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public DatosRespuesta crearRespuesta(DatosRegistroRespuesta datos) {

        Topico topico = topicoRepository.findById(datos.topico_id())
                .orElseThrow(() -> new ValidacionDeIntegridad("Tópico no encontrado"));

        Usuario usuario = usuarioRepository.findById(datos.usuario_id())
                .orElseThrow(() -> new ValidacionDeIntegridad("Usuario no encontrado"));

        Respuesta respuesta = new Respuesta(datos.mensaje(), usuario, topico);

        respuestaRepository.save(respuesta);

        return new DatosRespuesta(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion()
        );
    }

    public Respuesta retornarRespuestaPorId(Long id) {

        return respuestaRepository.getReferenceById(id);

    }

    public void eliminarRespuesta(Long id) {

        respuestaRepository.delete(retornarRespuestaPorId(id));

    }

    public DatosActualizarRespuesta actualizarRespuesta(DatosActualizarRespuesta datos) {
        Respuesta respuesta = retornarRespuestaPorId(datos.id());
        respuesta.setMensaje(datos.mensaje());
        return datos;
    }
}
