import pool from "../database.js"

const servingSizesController = {
    getServingSizes: async (req, res) => {
        const [rows] = await pool.query("SELECT * FROM serving_sizes")
        const servingSizes = rows
        res.send(servingSizes)
    },
    getServingSizeById: async (req, res) => {
        const { id } = req.params
        const [rows] = await pool.query(`
        SELECT *
        FROM serving_sizes
        WHERE serving_id = ?
        `, [id])
        const servingSize = rows[0]
        res.send(servingSize)
    },
    createServingSize: async (req, res) => {
        const {food_id, serving_name, serving_weight, serving_unit} = req.body
        const [result] = await pool.query(`
        INSERT INTO serving_sizes (food_id, serving_name, serving_weight, serving_unit) 
        VALUES (?, ?, ?, ?)
        `, [food_id, serving_name, serving_weight, serving_unit])
        const id = result.insertId
        const servingSize = getServingSizeById(id)
        res.send(servingSize)
    }
}

export default servingSizesController