package com.example.artgallery.model.repository;


import com.example.artgallery.model.Artist;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArtistRepository {
    private final BaseRepository repository;
    private List<Artist> artistList;

    public ArtistRepository() {
        this.repository = new BaseRepository();
        this.loadArtists();
    }

    public List<Artist> getArtistList() {
        return artistList;
    }

    public boolean addArtist(Artist artist) {
        String sql = "INSERT INTO Artist(name, birth_date, birth_place, nationality) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = repository.getConnection().prepareStatement(sql)) {
            stmt.setString(1, artist.getName());
            stmt.setDate(2, Date.valueOf(artist.getBirthDate()));
            stmt.setString(3, artist.getBirthPlace());
            stmt.setString(4, artist.getNationality());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteArtist(int artistId) {
        String sql = "DELETE FROM Artist WHERE id = ?";
        try (PreparedStatement stmt = repository.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, artistId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateArtist(int id, Artist artist) {
        String sql = "UPDATE Artist SET name = ?, birth_date = ?, birth_place = ?, nationality = ? WHERE id = ?";
        try (PreparedStatement stmt = repository.getConnection().prepareStatement(sql)) {
            stmt.setString(1, artist.getName());
            stmt.setDate(2, Date.valueOf(artist.getBirthDate()));
            stmt.setString(3, artist.getBirthPlace());
            stmt.setString(4, artist.getNationality());
            stmt.setInt(5, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void loadArtists() {
        String sql = "SELECT * FROM Artist ORDER BY name";
        artistList = new ArrayList<>();
        try (Statement stmt = repository.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                artistList.add(convertToArtist(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Artist> searchByName(String name) {
        List<Artist> result = new ArrayList<>();
        String sql = "SELECT * FROM Artist WHERE name LIKE ?";
        try (PreparedStatement stmt = repository.getConnection().prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(convertToArtist(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Artist convertToArtist(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        LocalDate birthDate = rs.getDate("birth_date").toLocalDate();
        String birthPlace = rs.getString("birth_place");
        String nationality = rs.getString("nationality");
        return new Artist(id, name, birthDate, birthPlace, nationality);
    }
}


