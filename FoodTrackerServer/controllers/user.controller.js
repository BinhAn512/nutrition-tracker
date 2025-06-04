import pool from "../database.js"

const userController = {
    getUsers: async (req, res) => {
        const [rows] = await pool.query("SELECT * FROM users")
        const users = rows
        res.send(users)
    },
    getUserByInfo: async (req, res) => {
        const { username, password } = req.query
        const [rows] = await pool.query(`
        SELECT *
        FROM users
        WHERE username = ? AND password = ?
        `, [username, password])
        const user = rows[0]
        res.send(user)
    },
    getUserByUsername: async (req, res) => {
        const { username } = req.params
        const [rows] = await pool.query(`
        SELECT *
        FROM users
        WHERE username = ?
        `, [username])
        const user = rows[0]
        res.send(user)
    },
    createUser: async (req, res) => {
        const {username, email, password} = req.body
        const [result] = await pool.query(`
        INSERT INTO users (username, email, password, created_at, updated_at) 
        VALUES (?, ?, ?, NOW(), NOW())
        `, [username, email, password])
        const user = result[0]
        res.send(user)
    }
}

export default userController