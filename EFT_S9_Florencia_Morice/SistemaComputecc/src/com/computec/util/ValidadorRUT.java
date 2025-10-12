package com.computec.util;

/**
 * Clase utilitaria para validar RUT chileno
 * Valida el formato y el dígito verificador
 */
public class ValidadorRUT {
    
    /**
     * Valida un RUT chileno completo
     * @param rut RUT en formato XX.XXX.XXX-X o XXXXXXXX-X
     * @return true si el RUT es válido, false en caso contrario
     */
    public static boolean validarRUT(String rut) {
        if (rut == null || rut.trim().isEmpty()) {
            return false;
        }
        
        // Eliminar puntos y espacios
        rut = rut.replace(".", "").replace(" ", "").toUpperCase();
        
        // Verificar formato básico (debe tener guión)
        if (!rut.contains("-")) {
            return false;
        }
        
        // Separar número y dígito verificador
        String[] partes = rut.split("-");
        if (partes.length != 2) {
            return false;
        }
        
        String numero = partes[0];
        String digitoVerificador = partes[1];
        
        // Validar que el número sea numérico
        try {
            Integer.parseInt(numero);
        } catch (NumberFormatException e) {
            return false;
        }
        
        // Validar que el dígito verificador sea válido (0-9 o K)
        if (!digitoVerificador.matches("[0-9K]")) {
            return false;
        }
        
        // Calcular dígito verificador esperado
        String dvCalculado = calcularDigitoVerificador(numero);
        
        // Comparar con el dígito ingresado
        return dvCalculado.equals(digitoVerificador);
    }
    
    /**
     * Calcula el dígito verificador de un RUT
     * @param rut Número de RUT sin dígito verificador
     * @return Dígito verificador calculado
     */
    public static String calcularDigitoVerificador(String rut) {
        int suma = 0;
        int multiplicador = 2;
        
        // Recorrer el RUT de derecha a izquierda
        for (int i = rut.length() - 1; i >= 0; i--) {
            suma += Character.getNumericValue(rut.charAt(i)) * multiplicador;
            multiplicador++;
            if (multiplicador > 7) {
                multiplicador = 2;
            }
        }
        
        // Calcular dígito verificador
        int resto = 11 - (suma % 11);
        
        if (resto == 11) {
            return "0";
        } else if (resto == 10) {
            return "K";
        } else {
            return String.valueOf(resto);
        }
    }
    
    /**
     * Formatea un RUT con puntos y guión
     * @param rut RUT sin formato
     * @return RUT formateado (XX.XXX.XXX-X)
     */
    public static String formatearRUT(String rut) {
        if (rut == null || rut.trim().isEmpty()) {
            return "";
        }
        
        // Eliminar formato previo
        rut = rut.replace(".", "").replace("-", "").replace(" ", "").toUpperCase();
        
        if (rut.length() < 2) {
            return rut;
        }
        
        // Separar número y dígito verificador
        String numero = rut.substring(0, rut.length() - 1);
        String dv = rut.substring(rut.length() - 1);
        
        // Agregar puntos al número
        StringBuilder rutFormateado = new StringBuilder();
        int contador = 0;
        
        for (int i = numero.length() - 1; i >= 0; i--) {
            if (contador == 3) {
                rutFormateado.insert(0, ".");
                contador = 0;
            }
            rutFormateado.insert(0, numero.charAt(i));
            contador++;
        }
        
        // Agregar guión y dígito verificador
        rutFormateado.append("-").append(dv);
        
        return rutFormateado.toString();
    }
}
