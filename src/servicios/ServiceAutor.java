/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import entidades.Autor;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Fer
 */
public class ServiceAutor {
    Scanner read = new Scanner(System.in);
    EntityManagerFactory emf= Persistence.createEntityManagerFactory("libreriaPU");
    EntityManager em= emf.createEntityManager();
    
    public Autor createAutorOnDB()
    {
        Autor autor = new Autor();
        System.out.println("Cual es el nombre del Autor que deseas introducir");
        String nombre = read.nextLine();
        
        if (nombre.equals(""))
        {
            System.out.println("No hay autor introducido");
        }
        else if(!nombre.equals(""))
        {
            autor.setNombre(nombre);
            try
                {
//                System.out.println("Introduce clave primaria");
//                autor.setId(read.next());
                em.getTransaction().begin();
                em.persist(autor);
                em.getTransaction().commit();
                
                
                }catch (Exception e)
                {
                    throw e;
                }
        } 
        return autor;
    }
    public void deleteAutorOnDB()
    {
        try 
        {
            System.out.println("Introduce la clave primaria del registro de autor +"
                    + "que quieras borrar");
            Autor autor = em.find(Autor.class, read.nextInt());
            em.getTransaction().begin();
            em.remove(autor);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }
    public void modifyAutor()
    {
        try {
            String nuevoNombre;
            System.out.println("Introduce la clave primaria del registro del autor +"
                    + "que quieras modificar");
            Autor autor = em.find(Autor.class, read.nextInt());
            System.out.println("Introduce el nuevo nombre del/a autore");
            nuevoNombre= read.nextLine();
            autor.setNombre(nuevoNombre);
            em.getTransaction().begin();
            em.merge(autor);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }
    public List <Autor> consultarAutores()
    {
        try {
            
            
            List <Autor> autores = em.createQuery("SELECT a "+" FROM Autor a").getResultList();
            
            for (Autor autore : autores) {
                //System.out.println("paso algo1");
                System.out.println(autore);
               // System.out.println("paso algo2  ");
            }
            return autores;
        } catch (Exception e) {
            throw e;
        }
    }
}
