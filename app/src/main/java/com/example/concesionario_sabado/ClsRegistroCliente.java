package com.example.concesionario_sabado;

public class ClsRegistroCliente {

    private String identificacion;
    private String nombre;
    private String direccion;
    private String telefono;
    private String activo;

    public ClsRegistroCliente(String identificacion, String nombre, String direccion,
                              String telefono, String activo) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.activo = activo;
    }//fin Constructor

    @Override
    public String toString() {
        return "ClsRegistroCliente{" +
                "identificacion='" + identificacion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", activo='" + activo + '\'' +
                '}';
    }

}
