package com.algaworks.domain.listener;

import com.algaworks.domain.event.PedidoConfirmadoEvent;
import com.algaworks.domain.model.Pedido;
import com.algaworks.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoConfirmadoListner {

    @Autowired
    private EnvioEmailService envioEmailService;

    @TransactionalEventListener
    public void aoConfimarPedido(PedidoConfirmadoEvent event){
        Pedido pedido = event.getPedido();

        EnvioEmailService.Mensagem mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
                .corpo("pedido-confirmado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmailService.enviar(mensagem);
    }

}
