import express from 'express';
import bodyParser from 'body-parser';
import foodsRouter from './routes/foods.router.js';
import favouriteFoodRouter from './routes/favourite_foods.router.js';
import foodLogsRouter from './routes/food_logs.router.js';
import macronutrientsRouter from './routes/macronutrients.router.js';
import mineralsRouter from './routes/minerals.router.js';
import servingSizesRouter from './routes/serving_sizes.router.js';
import userProfilesRouter from './routes/user_profiles.router.js';
import userRouter from './routes/user.router.js';
import vitaminsRouter from './routes/vitamins.router.js';

const app = express()

const PORT = process.env.PORT || 3000

app.use(express.json())
app.use(express.urlencoded({ extended: true }));

app.use(foodsRouter)
app.use(favouriteFoodRouter)
app.use(foodLogsRouter)
app.use(macronutrientsRouter)
app.use(mineralsRouter)
app.use(servingSizesRouter)
app.use(userProfilesRouter)
app.use(userRouter)
app.use(vitaminsRouter)

app.listen(PORT, () => {
    console.log('Server is running....')
})