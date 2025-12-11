/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.model;

/**
 *
 * @author ponlawatchangto
 */
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarDAO {

    private static final String BASE_CAR_QUERY =
            "SELECT c.*, b.brand_name, m.model_name, sm.sub_model_name, bt.body_type_name, gt.gear_name, ft.fuel_name " +
            "FROM carTable c " +
            "LEFT JOIN car_brands b ON c.brand_id = b.brand_id " +
            "LEFT JOIN car_models m ON c.model_id = m.model_id " +
            "LEFT JOIN car_sub_models sm ON c.sub_model_id = sm.sub_model_id " +
            "LEFT JOIN body_types bt ON c.body_type_id = bt.body_type_id " +
            "LEFT JOIN gear_types gt ON c.gear_id = gt.gear_id " +
            "LEFT JOIN fuel_types ft ON c.fuel_id = ft.fuel_id";

    private static List<Car> executeCarListQuery(String query, Object... params) {
        List<Car> carList = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                carList = makeCarListFromResultSet(rs);
            }
        } catch (Exception e) {
            Logger.getLogger(CarDAO.class.getName()).log(Level.SEVERE, "Error executing car list query", e);
            return null; // Or return an empty list depending on desired error handling
        }
        return carList;
    }

    public static List<Car> getAllCars() {
        String sql = BASE_CAR_QUERY + " WHERE c.status = 'Available'";
        return executeCarListQuery(sql);
    }

    public static List<Car> getCarsWithPicture() {
        System.out.println("CarDAO.getCarsWithPicture : Fetching cars with pictures");
        String sql = BASE_CAR_QUERY + " WHERE c.car_main_pic IS NOT NULL AND c.status = 'Available'";
        return executeCarListQuery(sql);
    }

    public static Car getCar(int id) {
        Car car = null;
        String statement = BASE_CAR_QUERY + " WHERE c.id = ? AND c.status = 'Available'";

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(statement);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            car = makeCarFromResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
            car = null;
        }
        return car;
    }

    public static boolean deleteCar(int id) {
        boolean success = false;
        String query = "delete from carTable where id = ?";

        try (Connection conn = DBConnection.getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            int rs = pstmt.executeUpdate();
            if (rs > 0) {
                System.out.println("Delete item id = " + id + " success !");
                success = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            // success is already false
        }
        return success;
    }

    public static boolean updateCarWithPicture(Car car, InputStream inputStream) {
        boolean success = false;

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            String query = "UPDATE carTable SET title = ?, brand_id = ?, model_id = ?, sub_model_id = ?, year = ?, body_type_id = ?, color = ?, mileage = ?, price = ?, gear_id = ?, fuel_id = ?, engine_displacement = ? ";

            if (inputStream != null) {
                query += " , car_main_pic = ? ";
            }
            query += " WHERE id = ? ";
            PreparedStatement pstmt = conn.prepareStatement(query);

            int paramCount = setPreparedStatement(pstmt, car);

            if (inputStream != null) {
                pstmt.setBlob(paramCount++, inputStream);
            }
            pstmt.setInt(paramCount, car.getId());
            int rs = pstmt.executeUpdate();
            if (rs > 0) {
                System.out.println("Update item id = " + car.getId() + " success !");
                success = true;
                conn.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // success is already false
        }
        return success;
    }

    public static boolean updateCar(Car car) {
        return updateCarWithPicture(car, null);
    }

    public static int insertCar(Car car, InputStream inputStream, int creatorId) {
        int generatedId = -1;
        String query = "INSERT INTO carTable (title, brand_id, model_id, sub_model_id, year, body_type_id, color, mileage, price, gear_id, fuel_id, engine_displacement, car_main_pic, creator_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            int paramCount = setPreparedStatement(pstmt, car);

            if (inputStream != null) {
                pstmt.setBlob(paramCount++, inputStream);
            } else {
                pstmt.setNull(paramCount++, Types.BLOB);
            }
            pstmt.setInt(paramCount++, creatorId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Insert new item success !");
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                    }
                }
            } else {
                System.out.println("Insert new item failed !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    public static Car makeCarFromResultSet(ResultSet rs) {
        Car car = new Car();
        try {
            if (rs != null && rs.next()) {
                car.setId(rs.getInt("id"));
                car.setTitle(rs.getString("title"));
                car.setBrandId(rs.getInt("brand_id"));
                car.setModelId(rs.getInt("model_id"));
                car.setSubModelId(rs.getInt("sub_model_id"));
                car.setYear(rs.getInt("year"));
                car.setMileage(rs.getInt("mileage"));
                car.setPrice(rs.getFloat("price"));
                car.setEngineDisplacement(rs.getInt("engine_displacement"));
                car.setBodyTypeId(rs.getInt("body_type_id"));
                car.setColor(rs.getString("color"));
                car.setGearId(rs.getInt("gear_id"));
                car.setFuelId(rs.getInt("fuel_id"));
                car.setStatus(rs.getString("status"));
                car.setCreator_id(rs.getInt("creator_id"));

                // Set names from JOINs
                car.setBrandName(rs.getString("brand_name"));
                car.setModelName(rs.getString("model_name"));
                car.setSubModelName(rs.getString("sub_model_name"));
                car.setBodyTypeName(rs.getString("body_type_name"));
                car.setGearName(rs.getString("gear_name"));
                car.setFuelTypeName(rs.getString("fuel_name"));

                // Use LocalDateTime object to get timestamp data type from sql
                LocalDateTime timeStamp = rs.getObject("created_at", LocalDateTime.class);
                // Check if timeStamp not null
                if (timeStamp != null) {
                    // Sure that timeStamp not null
                    car.setCreateDate(timeStamp.toLocalDate());
                    car.setCreateTime(timeStamp.toLocalTime());
                }

                byte[] imgData = rs.getBytes("car_main_pic");
                if (imgData != null) {
                    car.setCarMainPic(Base64.getEncoder().encodeToString(imgData));
                }
            }
        } catch (Exception e) {
            System.out.println("Error occurred in CarDAO.makeCarFormResultSet");
            e.printStackTrace();
            car = null;
        }
        return car;
    }

    public static List<Car> makeCarListFromResultSet(ResultSet rs) {
        List<Car> carList = new ArrayList<>();
        try {
            while (rs.next()) {
                Car car = new Car();
                car.setId(rs.getInt("id"));
                car.setTitle(rs.getString("title"));
                car.setBrandId(rs.getInt("brand_id"));
                car.setModelId(rs.getInt("model_id"));
                car.setSubModelId(rs.getInt("sub_model_id"));
                car.setYear(rs.getInt("year"));
                car.setMileage(rs.getInt("mileage"));
                car.setPrice(rs.getFloat("price"));
                car.setEngineDisplacement(rs.getInt("engine_displacement"));
                car.setBodyTypeId(rs.getInt("body_type_id"));
                car.setColor(rs.getString("color"));
                car.setGearId(rs.getInt("gear_id"));
                car.setFuelId(rs.getInt("fuel_id"));
                car.setStatus(rs.getString("status"));
                car.setCreator_id(rs.getInt("creator_id"));

                // Set names from JOINs
                car.setBrandName(rs.getString("brand_name"));
                car.setModelName(rs.getString("model_name"));
                car.setSubModelName(rs.getString("sub_model_name"));
                car.setBodyTypeName(rs.getString("body_type_name"));
                car.setGearName(rs.getString("gear_name"));
                car.setFuelTypeName(rs.getString("fuel_name"));

                // Use LocalDateTime object to get timestamp data type from sql
                LocalDateTime timeStamp = rs.getObject("created_at", LocalDateTime.class);
                // Check if timeStamp not null
                if (timeStamp != null) {
                    // Sure that timeStamp not null
                    car.setCreateDate(timeStamp.toLocalDate());
                    car.setCreateTime(timeStamp.toLocalTime());
                }

                byte[] imgData = rs.getBytes("car_main_pic");
                if (imgData != null) {
                    car.setCarMainPic(Base64.getEncoder().encodeToString(imgData));
                }

                carList.add(car);
            }
        } catch (Exception e) {
            System.out.println("Error occurred in CarDAO.makeCarListFormResultSet");
            e.printStackTrace();
            carList = null;
        }
        return carList;
    }

    public static int setPreparedStatement(PreparedStatement pstmt, Car car) throws SQLException {
        int count = 1;
        pstmt.setString(count++, car.getTitle());
        pstmt.setInt(count++, car.getBrandId());
        pstmt.setInt(count++, car.getModelId());
        pstmt.setInt(count++, car.getSubModelId());
        pstmt.setInt(count++, car.getYear());
        pstmt.setInt(count++, car.getBodyTypeId());
        pstmt.setString(count++, car.getColor());
        pstmt.setInt(count++, car.getMileage());
        pstmt.setFloat(count++, car.getPrice());
        pstmt.setInt(count++, car.getGearId());
        pstmt.setInt(count++, car.getFuelId());
        pstmt.setInt(count++, car.getEngineDisplacement());
        return count;
    }

    public static int getCarAmount() {
        int amount = 0;
        String sql = "SELECT COUNT(*) FROM carTable WHERE status = 'Available'";
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                amount = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return amount;
    }

    public static int getCarCountByBrands(String[] brandIds) {
        int amount = 0;
        String sql = "SELECT COUNT(*) FROM carTable WHERE status = 'Available'";

        if (brandIds != null && brandIds.length > 0) {
            sql += " AND brand_id IN (";
            for (int i = 0; i < brandIds.length; i++) {
                sql += "?";
                if (i < brandIds.length - 1) {
                    sql += ",";
                }
            }
            sql += ")";
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (brandIds != null && brandIds.length > 0) {
                for (int i = 0; i < brandIds.length; i++) {
                    pstmt.setInt(i + 1, Integer.parseInt(brandIds[i]));
                }
            }

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                amount = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return amount;
    }

    public static List<Car> getCarEachPage(int offset) {
        if (offset >= 0) {
            String sql = BASE_CAR_QUERY + " WHERE c.status = 'Available' LIMIT 6 OFFSET ?";
            System.out.println("getCarEachPage in CarDAO return carList normally");
            return executeCarListQuery(sql, offset);
        }
        System.out.println("getCarEachPage in CarDAO return null");
        return null;
    }

    public static boolean checkAvailable(int id) {
        System.out.println("Entering CarDAO.checkAvailable");
        String sql = "SELECT status FROM carTable WHERE id = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                System.out.println("Exiting CarDAO.checkAvailable");
                return rs.getString("status").equals("Available");
            }
        } catch (Exception e) {
            System.out.println("Error occurred in CarDAO.checkAvailable");
            System.out.println("Exiting CarDAO.checkAvailable");
            e.printStackTrace();
            return false;
        }
        return false ;
    }

    public static boolean ReserveCar(int id) {
        System.out.println("-----------------------------");
        System.out.println("Entering CarDAO.ReserveCar");
        String sql = "UPDATE carTable SET status = 'Reserved' WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Exiting CarDAO.ReserveCar");
            System.out.println("-----------------------------");
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Error occurred in CarDAO.ReserveCar");
            System.out.println("Exiting CarDAO.ReserveCar");
            System.out.println("-----------------------------");
            e.printStackTrace();
            return false;
        }
    }
}
