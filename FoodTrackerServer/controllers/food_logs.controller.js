import pool from "../database.js"

const foodLogsController = {
    getFoodLogs: async (req, res) => {
        const [rows] = await pool.query("SELECT * FROM food_logs")
        const food_logs = rows
        res.send(food_logs)
    },
    getFoodLogById: async (req, res) => {
        const { id } = req.params
        const [rows] = await pool.query(`
        SELECT *
        FROM food_logs
        WHERE log_id = ?
        `, [id])
        const food_log = rows[0]
        res.send(food_log)
    },
    getFoodByDay: async (req, res) => {
        const { date, user_id } = req.query
        const [food] = await pool.query(`
            SELECT f.food_id, f.caloric_value, m.fat, m.carbohydrates, m.protein, fl.serving_size
            FROM food_logs fl
            JOIN foods f ON f.food_id = fl.food_id
            JOIN macronutrients m ON m.food_id = f.food_id
            WHERE DATE(consumed_at) = ? AND user_id = ?
        `, [date, user_id])
        res.send(food)
    },
    getFoodByMeal: async (req, res) => {
        const { date, meal_type } = req.query
        const [food] = await pool.query(`
            SELECT f.food_id, f.food_name, f.caloric_value, f.nutrition_density
            FROM food_logs fl
            JOIN foods f ON f.food_id = fl.food_id
            WHERE DATE(consumed_at) = ?
            AND meal_type = ?
        `, [date, meal_type])
        res.send(food)
    },
    getMacroByDay: async (req, res) => {
        const {date} = req.params
        const [macro] = await pool.query(`
            SELECT m.macro_id, m.food_id, m.fat, m.saturated_fats, m.monounsaturated_fats, m.polyunsaturated_fats, m.carbohydrates, 
            m.sugars, m.protein, m.dietary_fiber, m.cholesterol, m.sodium, m.water
            FROM food_logs fl
            JOIN macronutrients m ON m.food_id = fl.food_id
            WHERE DATE(consumed_at) = ?
        `, [date])
        res.send(macro)
    },
    createFoodLog: async (req, res) => {
        const {user_id, food_id, serving_size, serving_unit, meal_type, consumed_at, notes} = req.body
        const [result] = await pool.query(`
        INSERT INTO food_logs (user_id, food_id, serving_size, serving_unit, meal_type, consumed_at, notes) 
        VALUES (?, ?, ?, ?, ?, ?, ?)
        `, [user_id, food_id, serving_size, serving_unit, meal_type, consumed_at, notes])
        
    }
}

export default foodLogsController;