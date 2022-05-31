/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria;



import servicios.ServiceAutor;
import servicios.ServiceEditorial;
import servicios.ServiceLibro;


/**
 *
 * @author Fer
 */
public class Libreria {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     
        try {
               ServiceAutor serviAutor = new ServiceAutor();
               ServiceEditorial serviEditorial = new ServiceEditorial();
               ServiceLibro serviLibro = new ServiceLibro();
               
//               serviAutor.createAutorOnDB();
//               serviAutor.createAutorOnDB();
//               serviEditorial.createEditorialOnDB();
//               serviEditorial.createEditorialOnDB();
               serviLibro.createLibroOnDB();
               //serviAutor.consultarAutores();
            
            
        } catch (Exception e) {
        }
    }
    
}
