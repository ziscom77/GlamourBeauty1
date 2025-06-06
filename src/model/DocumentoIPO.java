package model;

public record DocumentoIPO(
        int     id,
        String  tipo,
        String  descripcion,
        String  estado,
        Integer proveedorId,
        Integer inversionistaId
) {}

