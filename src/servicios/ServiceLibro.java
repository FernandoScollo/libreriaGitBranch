/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import entidades.Autor;
import entidades.Editorial;
import entidades.Libro;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Fer
 */
public class ServiceLibro 
{
    Scanner read = new Scanner(System.in);
    EntityManagerFactory emf= Persistence.createEntityManagerFactory("libreriaPU");
    EntityManager em= emf.createEntityManager();
    
    public Libro createLibroOnDB() throws Exception
    {
     try{
         Autor autor= new Autor();
         Editorial editorial = new Editorial();
         ServiceAutor serviAutor= new ServiceAutor();
         ServiceEditorial serviEditorial= new ServiceEditorial();
         // Nos fijamos los libros
         System.out.println("Elije un autor de la lista");
         List <Autor> autores = serviAutor.consultarAutores();
         
            for (Autor autore : autores) {
                System.out.println(autore);
                System.out.println("Introduce 'si' si quieres seleccionar el autor anterior");
                String selecAutor=  read.next();
                if (selecAutor.equals("si"))
                {
                    autor= autore;
                }
            }
            List <Editorial> editoriales= serviEditorial.consultarEditoriales(); //em.createQuery("SELECT e FROM Editorial e;").getResultList();
            for (Editorial editoriale : editoriales) {
                System.out.println(editoriale);
                System.out.println("Introduce 'si' si quieres seleccionar la editorial anterior");
                String selecAutor=  read.nextLine();
                if (selecAutor.equals("si"))
                {
                    editorial= editoriale;
                }
            }
         
        if (autor==null)
        {
            throw new Exception("No me jodas");
        }
        else if (editorial == null)
        {
            throw new Exception("No me jodas 2");
        }
        else
        {
        Libro book = new Libro();
        System.out.println("Introduce el Titulo del libro");
        book.setTitulo(read.nextLine());
        System.out.println("Introduce el anio del libro");
        book.setAnio(read.nextInt());
        book.setAutor(autor);
        book.setEditorial(editorial);
        System.out.println("Introduce el total de ejmplares de la libreria");
        book.setEjemplares(read.nextInt());
        book.setEjemplaresPrestados(0);
        book.setEjemplaresRestantes(book.getEjemplares());
        em.getTransaction().begin();
        em.persist(book);
        em.getTransaction().commit();
        return book;
        }
        }catch (Exception e)
        {
                throw e;
                
        }
    }
    public void prestarLibro(Libro libro)
    {
        System.out.println("Cuantos libros se prestaran?");
        int prestados = read.nextInt();
        if (prestados>libro.getEjemplaresRestantes())
        {
            System.out.println("Son demasiados libros para prestar, no se cuenta con esa cantidad");
        }
        else if (prestados<=libro.getEjemplaresRestantes())
        {
            libro.setEjemplaresPrestados(prestados);
            libro.setEjemplaresRestantes(libro.getEjemplaresRestantes()-libro.getEjemplaresPrestados());
        }
        
    }
    public void devolverLibro(Libro libro)
    {
        System.out.println("Cuantos libros se devuelven?");
        int devueltos = read.nextInt();
        if(devueltos >libro.getEjemplares())
        {
            System.out.println("uhmm si es muy bueno para ser cierto probablemente lo sea");
        }
        else if(devueltos<=libro.getEjemplares()-libro.getEjemplaresRestantes())
        {
            libro.setEjemplaresRestantes(libro.getEjemplaresRestantes()+devueltos);
            libro.setEjemplaresPrestados(libro.getEjemplaresPrestados()-devueltos);
            System.out.println("Gracias");
        }
    }
    public void deleteLibroFromDB()
    {
        try {
            System.out.println("Introduce la ID del libro que deseas borrar");
            Libro libro = em.find(Libro.class, read.next());
            em.getTransaction().begin();
            em.remove(libro);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }
    public void modifyLibro()
    {
        try {
            String modificacion;
            System.out.println("Introduce la ID del libro que deseas modificar");
            Libro libro = em.find(Libro.class, read.next());
            System.out.println("Que deseas modificar?");
            modificacion= read.next(); //Titulo
            if (modificacion.equals("titulo"))
            {
                System.out.println("Introduce el nuevo titulo del libro");
                String newTitulo= read.nextLine();
                libro.setTitulo(newTitulo);
                em.getTransaction().begin();
                em.merge(libro);
                em.getTransaction().commit();
            } //Anio
            else if(modificacion.equals("año"))
            {
                System.out.println("Introduce el nuevo año del libro");
                Integer newAnio= read.nextInt();
                libro.setAnio(newAnio);
                em.getTransaction().begin();
                em.merge(libro);
                em.getTransaction().commit();
            } //Ejemplares
            else if(modificacion.equals("ejemplares"))
            {
                System.out.println("Introduce la nueva cantidad de ejemplares de este libro");
                Integer newEjemplares= read.nextInt();
                libro.setEjemplares(newEjemplares);
                em.getTransaction().begin();
                em.merge(libro);
                em.getTransaction().commit();
            } //Autor
            else if(modificacion.equals("autor"))
            {
                String respuesta;
                String respuesta2;
                //es un autor diferente pero ya existe en la base de datos?
                System.out.println("es un autor diferente pero ya existe en la base de datos?");
                respuesta= read.nextLine();
                if(respuesta.equals("si"))
                {
                    System.out.println("Introduce la clave primaria del registro del autor +"
                    + "que quieras buscar");
                Autor autor = em.find(Autor.class, read.nextInt());
                libro.setAutor(autor);
                em.getTransaction().begin();
                em.merge(libro);
                em.getTransaction().commit();
                }
                //es un nuevo autor?
                System.out.println("es un autor que no existe en la base de datos?");
                respuesta2= read.next();
                if(respuesta2.equals("si"))
                {
                    ServiceAutor serviAutor= new ServiceAutor();
                    Autor autor=serviAutor.createAutorOnDB();
                    libro.setAutor(autor);
                    em.getTransaction().begin();
                    em.merge(libro);
                    em.getTransaction().commit();
                }
                
            } //Editorial
            else if(modificacion.equals("editorial"))
            {
                String respuesta;
                String respuesta2;
                //es un editorial diferente pero ya existe en la base de datos?
                System.out.println("es una editorial diferente pero ya existe en la base de datos?");
                respuesta= read.nextLine();
                if(respuesta.equals("si"))
                {
                    System.out.println("Introduce la clave primaria del registro de la editorial +"
                    + "que quieras buscar");
                Editorial editorial = em.find(Editorial.class, read.nextInt());
                libro.setEditorial(editorial);
                em.getTransaction().begin();
                em.merge(libro);
                em.getTransaction().commit();
                }
                //es una nueva editorial?
                System.out.println("es una editorial que no existe en la base de datos?");
                respuesta2= read.next();
                if(respuesta2.equals("si"))
                {
                    ServiceEditorial serviEditorial= new ServiceEditorial();
                    Editorial editorial=serviEditorial.createEditorialOnDB();
                    libro.setEditorial(editorial);
                    em.getTransaction().begin();
                    em.merge(libro);
                    em.getTransaction().commit();
                }
                
            }
        } catch (Exception e) {
            throw e;
        }
    }
    public void consultarLibros()
    {
        try {
            List <Libro> libros = em.createNamedQuery("SELECT l FROM Libro l;").getResultList();
            for (Libro libro : libros) {
                System.out.println(libro);
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
}
