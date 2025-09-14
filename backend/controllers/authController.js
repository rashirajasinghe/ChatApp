const User = require('../models/User');
const passport = require('passport');

// Google OAuth callback
const googleCallback = (req, res) => {
  // Successful authentication, redirect to frontend
  res.redirect(`${process.env.CLIENT_URL}/dashboard`);
};

// Get current user
const getCurrentUser = (req, res) => {
  if (req.isAuthenticated()) {
    res.json({
      id: req.user._id,
      email: req.user.email,
      name: req.user.name,
      picture: req.user.picture,
      role: req.user.role
    });
  } else {
    res.status(401).json({ message: 'Not authenticated' });
  }
};

// Logout user
const logout = (req, res) => {
  req.logout((err) => {
    if (err) {
      return res.status(500).json({ message: 'Error during logout' });
    }
    res.json({ message: 'Logged out successfully' });
  });
};

// Google OAuth strategy setup
const setupGoogleStrategy = () => {
  const GoogleStrategy = require('passport-google-oauth20').Strategy;
  
  passport.use(new GoogleStrategy({
    clientID: process.env.GOOGLE_CLIENT_ID,
    clientSecret: process.env.GOOGLE_CLIENT_SECRET,
    callbackURL: "/api/auth/google/callback"
  },
  async (accessToken, refreshToken, profile, done) => {
    try {
      // Check if user already exists
      let user = await User.findOne({ googleId: profile.id });
      
      if (user) {
        return done(null, user);
      }
      
      // Create new user
      user = new User({
        googleId: profile.id,
        email: profile.emails[0].value,
        name: profile.displayName,
        picture: profile.photos[0].value,
        role: 'admin' // All users are admins in this system
      });
      
      await user.save();
      return done(null, user);
    } catch (error) {
      return done(error, null);
    }
  }));
  
  // Serialize user for session
  passport.serializeUser((user, done) => {
    done(null, user._id);
  });
  
  // Deserialize user from session
  passport.deserializeUser(async (id, done) => {
    try {
      const user = await User.findById(id);
      done(null, user);
    } catch (error) {
      done(error, null);
    }
  });
};

module.exports = {
  googleCallback,
  getCurrentUser,
  logout,
  setupGoogleStrategy
};
