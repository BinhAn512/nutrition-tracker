import pool from "../database.js"

const favouriteFoodsController = {
    getFavouriteFoods: async (req, res) => {
        const {user_id} = req.params
        const [rows] = await pool.query(`
            SELECT * 
            FROM foods 
            WHERE food_id IN (SELECT food_id FROM favourite_foods WHERE user_id = ?) 
            `, [user_id])
        const foods = rows
        res.send(foods)
    },
    getFavouriteFood: async (req, res) => {
        const { user_id, food_id } = req.query
        const [rows] = await pool.query(`
        SELECT *
        FROM favourite_foods
        WHERE user_id = ?
        AND food_id = ?
        `, [user_id, food_id])
        const favourite_food = rows[0]
        res.send(favourite_food)
    },
    createFavouriteFood: async (req, res) => {
        const {user_id, food_id} = req.body
        const [result] = await pool.query(`
        INSERT INTO favourite_foods (user_id, food_id) 
        VALUES (?, ?)
        `, [user_id, food_id])
        // const row = result
        res.send(result)
        console.log(result)
    },
    deleteFavouriteFood: async (req, res) => {
        const {user_id, food_id} = req.query;
        const [result] = await pool.query(`
          DELETE FROM favourite_foods
          WHERE user_id = ?
          AND food_id = ?  
        `, [user_id, food_id])
        res.send(result)
        console.log(result)
    }
}

export default favouriteFoodsController;