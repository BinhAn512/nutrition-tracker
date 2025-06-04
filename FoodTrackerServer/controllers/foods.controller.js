import pool from "../database.js"

const foodsController = {
    getFoods: async (req, res) => {
        const [rows] = await pool.query("SELECT * FROM foods")
        const foods = rows
        res.send(foods)
    },
    getFoodById: async (req, res) => {
        const { id } = req.params
        const [rows] = await pool.query(`
        SELECT *
        FROM foods
        WHERE food_id = ?
        `, [id])
        const food = rows[0]
        res.send(food)
    },
    createFood: async (req, res) => {
        const {food_name, caloric_value, nutrition_density} = req.body
        const [result] = await pool.query(`
        INSERT INTO foods (food_name, caloric_value, nutrition_density, created_at, updated_at) 
        VALUES (?, ?, ?, NOW(), NOW())
        `, [food_name, caloric_value, nutrition_density])
        const id = result.insertId
        const food = getFoodById(id)
        res.send(food)
    }
}

export default foodsController;