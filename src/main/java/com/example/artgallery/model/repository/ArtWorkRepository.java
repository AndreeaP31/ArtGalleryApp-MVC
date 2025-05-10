package com.example.artgallery.model.repository;


import com.example.artgallery.model.ArtWork;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtWorkRepository {
    private final BaseRepository repository;
    private List<ArtWork> artWorks;

    public ArtWorkRepository() {
        this.repository = new BaseRepository();
        this.loadArtWorks();
    }

    public List<ArtWork> getArtWorks() {
        return artWorks;
    }

    public boolean addArtWork(ArtWork artWork) {
        String sql = "INSERT INTO ArtWork(title, type, price, artist_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = repository.getConnection().prepareStatement(sql)) {
            stmt.setString(1, artWork.getTitle());
            stmt.setString(2, artWork.getType());
            stmt.setDouble(3, artWork.getPrice());
            stmt.setInt(4, artWork.getArtistId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteArtWork(int id) {
        String sql = "DELETE FROM ArtWork WHERE id = ?";
        try (PreparedStatement stmt = repository.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateArtWork(int id, ArtWork artWork) {
        String sql = "UPDATE ArtWork SET title = ?, type = ?, price = ?, artist_id = ? WHERE id = ?";
        try (PreparedStatement stmt = repository.getConnection().prepareStatement(sql)) {
            stmt.setString(1, artWork.getTitle());
            stmt.setString(2, artWork.getType());
            stmt.setDouble(3, artWork.getPrice());
            stmt.setInt(4, artWork.getArtistId());
            stmt.setInt(5, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void loadArtWorks() {
        artWorks = new ArrayList<>();
        String sql = "SELECT * FROM ArtWork ORDER BY title";
        try (Statement stmt = repository.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                artWorks.add(convertToArtWork(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ArtWork> searchByTitle(String title) {
        List<ArtWork> result = new ArrayList<>();
        String sql = "SELECT * FROM ArtWork WHERE title LIKE ?";
        try (PreparedStatement stmt = repository.getConnection().prepareStatement(sql)) {
            stmt.setString(1, "%" + title + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(convertToArtWork(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public List<ArtWork> searchByArtistName(String artistName) {
        List<ArtWork> result = new ArrayList<>();
        String sql = "SELECT * FROM ArtWork aw JOIN Artist a ON aw.artist_id = a.id WHERE a.name LIKE ?";
        try (PreparedStatement stmt = repository.getConnection().prepareStatement(sql)) {
            stmt.setString(1, "%" + artistName + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(convertToArtWork(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<ArtWork> filterByType(String type) {
        List<ArtWork> result = new ArrayList<>();
        String sql = "SELECT * FROM ArtWork WHERE type = ?";
        try (PreparedStatement stmt = repository.getConnection().prepareStatement(sql)) {
            stmt.setString(1, type);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(convertToArtWork(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public List<ArtWork> filterByPriceRange(double minPrice, double maxPrice) {
        List<ArtWork> result = new ArrayList<>();
        String sql = "SELECT * FROM ArtWork WHERE price BETWEEN ? AND ?";
        try (PreparedStatement stmt = repository.getConnection().prepareStatement(sql)) {
            stmt.setDouble(1, minPrice);
            stmt.setDouble(2, maxPrice);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(convertToArtWork(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    private List<ArtWork> filterBy(String column, String value) {
        List<ArtWork> result = new ArrayList<>();
        String sql = "SELECT * FROM ArtWork WHERE " + column + " = ?";
        try (PreparedStatement stmt = repository.getConnection().prepareStatement(sql)) {
            stmt.setString(1, value);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(convertToArtWork(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private ArtWork convertToArtWork(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String type = rs.getString("type");
        double price = rs.getDouble("price");
        int artistId = rs.getInt("artist_id");
        return new ArtWork(id, title, type, price, artistId);
    }
}
