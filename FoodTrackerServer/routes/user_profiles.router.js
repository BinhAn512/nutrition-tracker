import express from 'express'
import userProfilesController from '../controllers/user_profiles.controller.js'

const userProfilesRouter = express.Router()

userProfilesRouter.get("/user_profiles", userProfilesController.getUserProfiles)
userProfilesRouter.get("/user_profile/:id", userProfilesController.getUserProfileById)

userProfilesRouter.post("/user_profile", userProfilesController.createUserProfile);

export default userProfilesRouter