import pool from "../database.js"

const mineralsController = {
    getMinerals: async (req, res) => {
        const [rows] = await pool.query("SELECT * FROM minerals")
        const minerals = rows
        res.send(minerals)
    },
    getMineralById: async (req, res) => {
        const { id } = req.params
        const [rows] = await pool.query(`
        SELECT *
        FROM minerals
        WHERE food_id = ?
        `, [id])
        const mineral = rows[0]
        res.send(mineral)
    },
    createMineral: async (req, res) => {
        const {food_id, calcium, copper, iron, magnesium, manganese, phosphorus, potassium, selenium, zinc} = req.body
        const [result] = await pool.query(`
        INSERT INTO minerals (food_id, calcium, copper, iron, magnesium, manganese, phosphorus, potassium, selenium, zinc) 
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        `, [food_id, calcium, copper, iron, magnesium, manganese, phosphorus, potassium, selenium, zinc])
        const id = result.insertId
        const mineral = getMineralById(id)
        res.send(mineral)
    }
}

export default mineralsController