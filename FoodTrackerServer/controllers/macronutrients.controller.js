import pool from "../database.js"

const macronutrientsController = {
    getMacronutrients: async (req, res) => {
        const [rows] = await pool.query("SELECT * FROM macronutrients")
        const macronutrients = rows
        res.send(macronutrients)
    },
    getMacronutrientById: async (req, res) => {
        const { id } = req.params
        const [rows] = await pool.query(`
        SELECT *
        FROM macronutrients
        WHERE food_id = ?
        `, [id])
        const macronutrient = rows[0]
        res.send(macronutrient)
    },
    createMacronutrients: async (req, res) => {
        const {food_id, fat, saturated_fat, monounsaturated_fats, polyunsaturated_fats, carbohydrates, 
            sugars, protein, dietary_fiber, cholesterol, sodium, water
        } = req.body
        const [result] = await pool.query(`
        INSERT INTO macronutrients (food_id, fat, saturated_fats, monounsaturated_fats, polyunsaturated_fats, carbohydrates, 
            sugars, protein, dietary_fiber, cholesterol, sodium, water) 
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        `, [food_id, fat, saturated_fat, monounsaturated_fats, polyunsaturated_fats, carbohydrates, 
            sugars, protein, dietary_fiber, cholesterol, sodium, water])
        const id = result.insertId
        const macronutrient = getMacronutrientById(id)
        res.send(macronutrient)
    }
}

export default macronutrientsController;