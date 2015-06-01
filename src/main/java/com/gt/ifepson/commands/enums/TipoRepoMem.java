/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands.enums;

/**
     * Tipo de reporte
     */
    public enum TipoRepoMem {

        /**
         * Se envía ´T´ 0x54 para un Total General, sin detalle diario como Documento No Fiscal con centavos (“Reporte de Contador” resumido).
         */
        TotalGeneral("T", "Se envía ´T´ 0x54 para un Total General, sin detalle diario como Documento No Fiscal con centavos (“Reporte de Contador” resumido)."),
        /**
         * Se envía ´D´ 0x44 para un reporte detallado como Documento No Fiscal con centavos (“Reporte de Contador” con detalles).
         */
        ReporteContador("D", "Se envía ´D´ 0x44 para un reporte detallado como Documento No Fiscal con centavos (“Reporte de Contador” con detalles)."),
        /**
         * Se envía ´t´ 0x74 para un Total General, sin detalle diario como Documento Fiscal con redondeo al peso (“Informe de Auditoría” resumido).
         */
        InfoAuditResum("t", "Se envía ´t´ 0x74 para un Total General, sin detalle diario como Documento Fiscal con redondeo al peso (“Informe de Auditoría” resumido)."),
        /**
         * Se envía ´d´ 0x64 para un reporte detallado como Documento Fiscal con redondeo al peso (“Informe de Auditoría” con detalles).
         */
        InfoAuditDetalle("d", "Se envía ´d´ 0x64 para un reporte detallado como Documento Fiscal con redondeo al peso (“Informe de Auditoría” con detalles).");
        
        String letra = "";
        String descripcion = "";

        /**
         * abreviatura
         * @return 
         */
        public String getLetra() {
            return letra;
        }

        /**
         * descripcion
         * @return 
         */
        public String getDescripcion() {
            return descripcion;
        }

        TipoRepoMem(String letra, String descripcion) {
            this.letra = letra;
            this.descripcion = descripcion;
        }

        /**
         * abreviatura a TipoRepoMem
         * @param letra
         * @return 
         */
        public static TipoRepoMem parseLetra(String letra) {

            switch(letra.charAt(0)) {
                case 'T': return TipoRepoMem.TotalGeneral;
                case 'D': return TipoRepoMem.ReporteContador;
                case 't': return TipoRepoMem.InfoAuditResum;
                case 'd': return TipoRepoMem.InfoAuditDetalle;
            }

            return null;
        }
    }