import express from 'express'
import macronutrientsController from '../controllers/macronutrients.controller.js'

const macronutrientsRouter = express.Router()

macronutrientsRouter.get("/macronutrients", macronutrientsController.getMacronutrients)
macronutrientsRouter.get("/macronutrient/:id", macronutrientsController.getMacronutrientById)

macronutrientsRouter.post("/", macronutrientsController.createMacronutrients);

export default macronutrientsRouter