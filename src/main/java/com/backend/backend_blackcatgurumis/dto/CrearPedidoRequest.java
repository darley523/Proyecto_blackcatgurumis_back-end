package com.backend.backend_blackcatgurumis.dto;

import java.util.List;

import lombok.Data;

@Data
public class CrearPedidoRequest {
    private List<ItemPedidoDto> items;

}
