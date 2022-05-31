/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import entidades.Editorial;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Fer
 */
public class ServiceEditorial {
    Scanner read = new Scanner(System.in);
    EntityManagerFactory emf= Persistence.createEntityManagerFactory("libreriaPU");
    EntityManager em= emf.createEntityManager();
    public Editorial createEditorialOnDB()
    {
        Editorial editorial = new Editorial();
        System.out.println("Introduce el nombre de la editorial");
        String nombre = read.next();
        if (nombre.equals(""))
        {
            System.out.println("No hay autor introducido");
        }
        else if(!nombre.equals(""))
        {
            editorial.setNombre(nombre);
            try
                {
//                System.out.println("Introduce clave primaria");
//                editorial.setId(read.nextInt());
                em.getTransaction().begin();
                em.persist(editorial);
                em.getTransaction().commit();
                
                }catch (Exception e)
                {
                    throw e;
                }
        }
        
        return editorial;
    }

    public void deleteEditorialOnDb()
    {
        try 
        {
            System.out.println("Introduce la clave primaria del registro de la editorial +"
                    + "que quieras borrar");
            Editorial editorial = em.find(Editorial.class, read.nextInt());
            em.getTransaction().begin();
            em.remove(editorial);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }
    public void modifyEditorialOnDB()
    {
        try {
            String nuevoNombre;
            System.out.println("Introduce la clave primaria del registro de la editorial +"
                    + "que quieras modificar");
            Editorial editorial = em.find(Editorial.class, read.nextInt());
            System.out.println("Introduce el nuevo nombre d ela editorial");
            nuevoNombre= read.nextLine();
            editorial.setNombre(nuevoNombre);
            em.getTransaction().begin();
            em.merge(editorial);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }
    public List <Editorial> consultarEditoriales()
    {
        try {
            List <Editorial> editoriales=em.createQuery("SELECT e FROM Editorial e").getResultList();
            for (Editorial editoriale : editoriales) {
                System.out.println(editoriale);
            }
            return editoriales;
        } catch (Exception e) {
            throw e;
        }
    }
}
