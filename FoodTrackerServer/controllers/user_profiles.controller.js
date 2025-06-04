import pool from "../database.js"

const userProfilesController = {
    getUserProfiles: async (req, res) => {
        const [rows] = await pool.query("SELECT * FROM user_profiles")
        const servingSizes = rows
        res.send(servingSizes)
    },
    getUserProfileById: async (req, res) => {
        const { id } = req.params
        const [rows] = await pool.query(`
        SELECT *
        FROM user_profiles
        WHERE profile_id = ?
        `, [id])
        const userProfile = rows[0]
        res.send(userProfile)
    },
    createUserProfile: async (req, res) => {
        const {user_id, age, gender, weight, weight_goal, daily_calorie_goal} = req.body
        const [result] = await pool.query(`
        INSERT INTO user_profiles (user_id, age, gender, weight, weight_goal, daily_calorie_goal) 
        VALUES (?, ?, ?, ?, ?, ?)
        `, [user_id, age, gender, weight, weight_goal, daily_calorie_goal])
        const user_profile = result[0]
        res.send(user_profile)
    }
}

export default userProfilesController