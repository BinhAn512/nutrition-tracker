import express from 'express'
import userController from '../controllers/user.controller.js'

const userRouter = express.Router()

userRouter.get("/users", userController.getUsers)
userRouter.get("/user", userController.getUserByInfo)
userRouter.get("/user/:username", userController.getUserByUsername)

userRouter.post("/user", userController.createUser);

export default userRouter