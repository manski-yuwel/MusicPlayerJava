package com.clemente.musicplayer.dao;

import com.clemente.musicplayer.entity.Song;
import javax.persistence.*;
import java.util.List;

public class SongDAO {
    private EntityManagerFactory emf;
    
    public SongDAO() {
        emf = Persistence.createEntityManagerFactory("musicdata");
    }
    
    public void saveSong(Song song) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(song);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    public List<Song> getAllSongs() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT s FROM Song s", Song.class).getResultList();
        } finally {
            em.close();
        }
    }
    
    public Song findSongByTitle(String title) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Song> query = em.createQuery(
                "SELECT s FROM Song s WHERE s.title = :title", Song.class);
            query.setParameter("title", title);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public void updateSong(Song song) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(song);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    public void deleteSong(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Song song = em.find(Song.class, id);
            if (song != null) {
                em.remove(song);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    public void close() {
        if (emf != null) {
            emf.close();
        }
    }
}
