namespace PumpUp
{
    using Microsoft.Maui.Controls;
    public partial class MainPage : ContentPage
    {
        public MainPage()
        {
            InitializeComponent();
            CreateWeightButtons();
        }

        private void CreateWeightButtons()
        {
            for (int weight = 10; weight <= 100; weight += 10)
            {
                var button = new Button
                {
                    Text = $"{weight} kg",
                    FontSize = 20,
                    Margin = new Thickness(0, 5),
                    BackgroundColor = Colors.LightGray
                };

                int capturedWeight = weight;

                button.Clicked += (s, e) =>
                {
                    string imageName = $"strength_{capturedWeight}.png"; // Ensure image is in Resources/Images/
                    StrengthImage.Source = imageName;
                    StrengthImage.IsVisible = true;

                    StrengthTextLabel.Text = GetStrengthText(capturedWeight);
                    StrengthTextLabel.IsVisible = true;
                };

                ButtonContainer.Children.Add(button);
            }
        }

        private string GetStrengthText(int weight)
        {
            if (weight <= 20) return "You are very weak! Time to hit the gym!";
            if (weight <= 40) return "Getting there, keep pushing!";
            if (weight <= 60) return "Average strength, nice job!";
            if (weight <= 80) return "Strong! People are watching!";
            return "You are a beast! King of the gym!";
        }
    }
}


