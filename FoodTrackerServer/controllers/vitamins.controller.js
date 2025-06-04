import pool from "../database.js"

const vitaminsController = {
    getVitamins: async (req, res) => {
        const [rows] = await pool.query("SELECT * FROM vitamins")
        const vitamins = rows
        res.send(vitamins)
    },
    getVitaminById: async (req, res) => {
        const { id } = req.params
        const [rows] = await pool.query(`
        SELECT *
        FROM vitamins
        WHERE food_id = ?
        `, [id])
        const vitamin = rows[0]
        res.send(vitamin)
    },
    createVitamin: async (req, res) => {
        const {food_id, vitamin_a, vitamin_b1, vitamin_b11, vitamin_b12, vitamin_b2, vitamin_b3,
            vitamin_b5, vitamin_b6, vitamin_c, vitamin_d, vitamin_e, vitamin_k
        } = req.body
        const [result] = await pool.query(`
        INSERT INTO vitamins (food_id, vitamin_a, vitamin_b1, vitamin_b11, vitamin_b12, vitamin_b2, vitamin_b3,
            vitamin_b5, vitamin_b6, vitamin_c, vitamin_d, vitamin_e, vitamin_k) 
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        `, [food_id, vitamin_a, vitamin_b1, vitamin_b11, vitamin_b12, vitamin_b2, vitamin_b3,
            vitamin_b5, vitamin_b6, vitamin_c, vitamin_d, vitamin_e, vitamin_k])
        const id = result.insertId
        const vitamin = getUserById(id)
        res.send(vitamin)
    }
}

export default vitaminsController