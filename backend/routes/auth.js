const express = require('express');
const passport = require('passport');
const router = express.Router();
const { getCurrentUser, logout, googleCallback } = require('../controllers/authController');
const { isAuthenticated } = require('../middlewares/auth');

// Google OAuth routes
router.get('/google', 
  passport.authenticate('google', { scope: ['profile', 'email'] })
);

router.get('/google/callback',
  passport.authenticate('google', { failureRedirect: '/login' }),
  googleCallback
);

// Get current user
router.get('/me', isAuthenticated, getCurrentUser);

// Logout
router.post('/logout', logout);

module.exports = router;
